package AutoDevKit.util;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComposedRequest {
    private Map<String, String> headers = new HashMap<String, String>();
    private List<NewCookie> cookies = new ArrayList<NewCookie>();
    private MediaType mediaType;

    public ComposedRequest() {
    }

    public ComposedRequest(Map<String, String> headers, List<NewCookie> cookies, MediaType mediaType) {
        this.headers = headers;
        this.cookies = cookies;
        this.mediaType = mediaType;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public List<NewCookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<NewCookie> cookies) {
        this.cookies = cookies;
    }

    public void addCookies(List<NewCookie> cookies) {
        this.cookies.addAll(cookies);
    }

    public void addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    public void addHeader(Map.Entry<String, String> header) {
        this.headers.put(header.getKey(), header.getValue());
    }

    public void addHeaderIfNotExists(String key, String value) {
        this.headers.putIfAbsent(key, value);
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComposedRequest that = (ComposedRequest) o;

        if (headers != null ? !headers.equals(that.headers) : that.headers != null) return false;
        return cookies != null ? cookies.equals(that.cookies) : that.cookies == null;

    }

    @Override
    public int hashCode() {
        int result = headers != null ? headers.hashCode() : 0;
        result = 31 * result + (cookies != null ? cookies.hashCode() : 0);
        return result;
    }
}

