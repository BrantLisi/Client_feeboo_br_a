/*
* -----------------------------------------------------------------------------------
* Copyright (C) 2004-2015, by FacilityONE Head Office Co. Ltd. All rights reserved.
* -----------------------------------------------------------------------------------
*
* File: OauthHttpClient.java
* Author: tessi.lu
* Version: 1.0
* Create: 2015-04-02
*
* Changes (from 2015-04-02)
* -----------------------------------------------------------------------------------
* Modify:
* -----------------------------------------------------------------------------------
*/

package com.brantf.wireless.br_library.oauth2;

import com.android.volley.AuthFailureError;
import com.android.volley.ServerError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ByteArrayPool;
import com.android.volley.toolbox.PoolingByteArrayOutputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/**
 * 基于{@link HttpURLConnection}的Http网络请求类。
 * @author tessi.lu
 * @version V1.0，2015-04-02
 * @see
 * @since Shang
 */
public class OauthHttpClient {
	private static final String HEADER_CONTENT_TYPE = "Content-Type";
	private static final String SET_COOKIE_KEY = "Set-Cookie";
	private static final String COOKIE_KEY = "Cookie";
	private static final String SESSION_COOKIE = "JSESSIONID";
	
	private static String session = "";

	private static final int TIMEOUT = 20000;
	
	/**
	 * An interface for transforming URLs before use.
	 */
	public interface UrlRewriter {
		/**
		 * Returns a URL to use instead of the provided one, or null to indicate
		 * this URL should not be used at all.
		 */
		public String rewriteUrl(String originalUrl);
	}

	private final UrlRewriter mUrlRewriter;
	private final SSLSocketFactory mSslSocketFactory;

	public OauthHttpClient() {
		this(null);
	}

	/**
	 * @param urlRewriter
	 *            Rewriter to use for request URLs
	 */
	public OauthHttpClient(UrlRewriter urlRewriter) {
		this(urlRewriter, null);
	}

	/**
	 * @param urlRewriter
	 *            Rewriter to use for request URLs
	 * @param sslSocketFactory
	 *            SSL factory to use for HTTPS connections
	 */
	public OauthHttpClient(UrlRewriter urlRewriter,
			SSLSocketFactory sslSocketFactory) {
		mUrlRewriter = urlRewriter;
		mSslSocketFactory = sslSocketFactory;
	}

	/**
	 * Initializes an {@link HttpEntity} from the given
	 * {@link HttpURLConnection}.
	 * 
	 * @param connection
	 * @return an HttpEntity populated with data from <code>connection</code>.
	 */
	private static HttpEntity entityFromConnection(HttpURLConnection connection) {
		BasicHttpEntity entity = new BasicHttpEntity();
		InputStream inputStream;
		try {
			inputStream = connection.getInputStream();
		} catch (IOException ioe) {
			inputStream = connection.getErrorStream();
		}
		entity.setContent(inputStream);
		entity.setContentLength(connection.getContentLength());
		entity.setContentEncoding(connection.getContentEncoding());
		entity.setContentType(connection.getContentType());
		return entity;
	}

	/**
	 * Create an {@link HttpURLConnection} for the specified {@code url}.
	 */
	protected HttpURLConnection createConnection(URL url) throws IOException {
		return (HttpURLConnection) url.openConnection();
	}

	/**
	 * Opens an {@link HttpURLConnection} with parameters.
	 * 
	 * @param url
	 * @return an open connection
	 * @throws IOException
	 */
	private HttpURLConnection openConnection(URL url) throws IOException {
		HttpURLConnection connection = createConnection(url);

		int timeoutMs = TIMEOUT;
		connection.setConnectTimeout(timeoutMs);
		connection.setReadTimeout(timeoutMs);
		connection.setUseCaches(false);
		connection.setDoInput(true);

		// use caller-provided custom SslSocketFactory, if any, for HTTPS
		if ("https".equals(url.getProtocol()) && mSslSocketFactory != null) {
			((HttpsURLConnection) connection)
					.setSSLSocketFactory(mSslSocketFactory);
		}

		return connection;
	}

	public String login(String url, String entity,
                        HashMap<String, String> mHeaders) throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.putAll(mHeaders);
		HashMap<String, String> responseHeaders = new HashMap<String, String>();
		session = "";
		
		if (mUrlRewriter != null) {
			String rewritten = mUrlRewriter.rewriteUrl(url);
			if (rewritten == null) {
				throw new IOException("URL blocked by rewriter: " + url);
			}
			url = rewritten;
		}
		URL parsedUrl = new URL(url);
		HttpURLConnection connection = openConnection(parsedUrl);
		for (String headerName : map.keySet()) {
			connection.addRequestProperty(headerName, map.get(headerName));
		}
		connection.setRequestMethod("POST");
		connection.addRequestProperty(HEADER_CONTENT_TYPE, "application/json; charset=utf-8");
		try {
			addBodyIfExists(connection, entity);
		} catch (AuthFailureError e) {
			e.printStackTrace();
		}
		// Initialize HttpResponse with data from the HttpURLConnection.
		ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
		int responseCode = connection.getResponseCode();
		if (responseCode == -1) {
			// -1 is returned by getResponseCode() if the response code could
			// not be retrieved.
			// Signal to the caller that something was wrong with the
			// connection.
			throw new IOException(
					"Could not retrieve response code from HttpUrlConnection.");
		}
		StatusLine responseStatus = new BasicStatusLine(protocolVersion,
				connection.getResponseCode(), connection.getResponseMessage());
		BasicHttpResponse response = new BasicHttpResponse(responseStatus);
		response.setEntity(entityFromConnection(connection));
		for (Entry<String, List<String>> header : connection.getHeaderFields()
				.entrySet()) {
			if (header.getKey() != null) {
				Header h = new BasicHeader(header.getKey(), header.getValue()
						.get(0));
				response.addHeader(h);
				responseHeaders.put(h.getName(), h.getValue());
			}
		}
		
		String result = null;
		try {
			byte[] tmp = entityToBytes(response.getEntity());
			result = new String(tmp);
		} catch (ServerError e) {
			e.printStackTrace();
		}
		
		checkSessionCookie(responseHeaders); // Save cookie.
		
		return result;
	}

	// "http://192.168.1.102:8080/oauth2/auth?client_id=00000000&redirect_uri=http://192.168.1.102:8080/main.html&response_type=code"
	public String getCode(String url,
                          HashMap<String, String> mHeaders) throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.putAll(mHeaders);
		addSessionCookie(map);

		if (mUrlRewriter != null) {
			String rewritten = mUrlRewriter.rewriteUrl(url);
			if (rewritten == null) {
				throw new IOException("URL blocked by rewriter: " + url);
			}
			url = rewritten;
		}
		URL parsedUrl = new URL(url);
		HttpURLConnection connection = openConnection(parsedUrl);
		for (String headerName : map.keySet()) {
			connection.addRequestProperty(headerName, map.get(headerName));
		}
		connection.setRequestMethod("GET");
		// Initialize HttpResponse with data from the HttpURLConnection.
		ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
		int responseCode = connection.getResponseCode();
		if (responseCode == -1) {
			// -1 is returned by getResponseCode() if the response code could
			// not be retrieved.
			// Signal to the caller that something was wrong with the
			// connection.
			throw new IOException(
					"Could not retrieve response code from HttpUrlConnection.");
		}
		StatusLine responseStatus = new BasicStatusLine(protocolVersion,
				connection.getResponseCode(), connection.getResponseMessage());
		BasicHttpResponse response = new BasicHttpResponse(responseStatus);
		response.setEntity(entityFromConnection(connection));
		for (Entry<String, List<String>> header : connection.getHeaderFields()
				.entrySet()) {
			if (header.getKey() != null) {
				Header h = new BasicHeader(header.getKey(), header.getValue()
						.get(0));
				response.addHeader(h);
			}
		}

		String code = connection.getURL().getQuery();
		if (code != null) {
			code = code.replace("code=", "");
		}

		return code;
	}

	public String getAccessToken(String url,
                                 HashMap<String, String> mHeaders) throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.putAll(mHeaders);
		addSessionCookie(map);
		
		if (mUrlRewriter != null) {
			String rewritten = mUrlRewriter.rewriteUrl(url);
			if (rewritten == null) {
				throw new IOException("URL blocked by rewriter: " + url);
			}
			url = rewritten;
		}
		URL parsedUrl = new URL(url);
		HttpURLConnection connection = openConnection(parsedUrl);
		for (String headerName : map.keySet()) {
			connection.addRequestProperty(headerName, map.get(headerName));
		}
		
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.addRequestProperty(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
		
		// Initialize HttpResponse with data from the HttpURLConnection.
		ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
		int responseCode = connection.getResponseCode();
		if (responseCode == -1) {
			// -1 is returned by getResponseCode() if the response code could
			// not be retrieved.
			// Signal to the caller that something was wrong with the
			// connection.
			throw new IOException(
					"Could not retrieve response code from HttpUrlConnection.");
		}
		StatusLine responseStatus = new BasicStatusLine(protocolVersion,
				connection.getResponseCode(), connection.getResponseMessage());
		BasicHttpResponse response = new BasicHttpResponse(responseStatus);
		response.setEntity(entityFromConnection(connection));
		for (Entry<String, List<String>> header : connection.getHeaderFields()
				.entrySet()) {
			if (header.getKey() != null) {
				Header h = new BasicHeader(header.getKey(), header.getValue()
						.get(0));
				response.addHeader(h);
			}
		}

		String access_token = null;
		
		try {
			byte[] tmp = entityToBytes(response.getEntity());
			access_token = new String(tmp);
		} catch (ServerError e) {
			e.printStackTrace();
		}

		return access_token;
	}
	
	private byte[] entityToBytes(HttpEntity entity) throws IOException, ServerError {
		ByteArrayPool mPool = new ByteArrayPool(10240);
	    PoolingByteArrayOutputStream bytes =
	            new PoolingByteArrayOutputStream(mPool, (int) entity.getContentLength());
	    byte[] buffer = null;
	    try {
	        InputStream in = entity.getContent();
	        if (in == null) {
	            throw new ServerError();
	        }
	        buffer = mPool.getBuf(1024);
	        int count;
	        while ((count = in.read(buffer)) != -1) {
	            bytes.write(buffer, 0, count);
	        }
	        return bytes.toByteArray();
	    } finally {
	        try {
	            // Close the InputStream and release the resources by "consuming the content".
	            entity.consumeContent();
	        } catch (IOException e) {
	            // This can happen if there was an exception above that left the entity in
	            // an invalid state.
	            VolleyLog.v("Error occured when calling consumingContent");
	        }
	        mPool.returnBuf(buffer);
	        bytes.close();
	    }
	}
	
	private static void addBodyIfExists(HttpURLConnection connection,
			String entity) throws IOException, AuthFailureError {
		byte[] body = entity.getBytes();
		if (body != null) {
			connection.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			out.write(body);
			out.close();
		}
	}

	/**
	 * Checks the response headers for session cookie and saves it if it finds
	 * it.
	 * 
	 * @param headers
	 *            Response Headers.
	 */
	public final void checkSessionCookie(Map<String, String> headers) {
		if (headers.containsKey(SET_COOKIE_KEY)
				&& headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
			String cookie = headers.get(SET_COOKIE_KEY);
			if (cookie.length() > 0) {
				String[] splitCookie = cookie.split(";");
				String[] splitSessionId = splitCookie[0].split("=");
				cookie = splitSessionId[1];
				session = cookie;
			}
		}
	}

	/**
	 * Adds session cookie to headers if exists.
	 * 
	 * @param headers
	 */
	public final void addSessionCookie(Map<String, String> headers) {
		String sessionId = session;
		if (sessionId.length() > 0) {
			StringBuilder builder = new StringBuilder();
			builder.append(SESSION_COOKIE);
			builder.append("=");
			builder.append(sessionId);
			if (headers.containsKey(COOKIE_KEY)) {
				builder.append("; ");
				builder.append(headers.get(COOKIE_KEY));
			}
			headers.put(COOKIE_KEY, builder.toString());
		}
	}
}