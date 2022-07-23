package AutoDevKit;

import AutoDevKit.util.ComposedRequest;
import AutoDevKit.util.RequestData;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.ClientResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class RestClient {
    protected static Client client;

    public Response create(String url, String postData, ComposedRequest composedRequest, MediaType contentType) {
        return create(url, postData, composedRequest.getHeaders(), composedRequest.getCookies(), contentType);
    }

    public Response create(String url, String postData, Map<String, String> headers, List<NewCookie> cookies, MediaType contentType) {
        RequestData requestData = new RequestData();

        requestData.setHeaders(headers);
        requestData.setCookies(cookies);
        requestData.setContentType(contentType);
        requestData.setPostData(postData);
        requestData.setUrl(url);
        Invocation.Builder wrb = generateWebResource(requestData);
        return wrb.post(Entity.entity(requestData.getPostData() != null ? requestData.getPostData() : "", MediaType.APPLICATION_JSON));
    }

    public Response get(String url, Map<String, String> headers, List<NewCookie> cookies) {
        RequestData requestData = new RequestData();
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        requestData.setHeaders(headers);
        requestData.setCookies(cookies);
        requestData.setUrl(url);
        Invocation.Builder wrb = generateWebResource(requestData);
        return wrb.get();
    }
    public Response delete(String url, String jsonData, ComposedRequest composedRequest, MediaType contentType) {
        RequestData requestData = new RequestData();
        requestData.setContentType(contentType);
        Map<String, String> headers = composedRequest.getHeaders();
        if (headers == null) {
            headers = new HashMap<>();
            headers.put("X-HTTP-Method-Override", "DELETE");
        }
        requestData.setHeaders(headers);
        requestData.setHeaders(composedRequest.getHeaders());
        requestData.setCookies(composedRequest.getCookies());
        requestData.setPostData(jsonData);
        requestData.setUrl(url);
        Invocation.Builder wrb = generateWebResource(requestData);
        return wrb.method("DELETE",Entity.entity(jsonData,MediaType.APPLICATION_JSON));
    }

    private Invocation.Builder generateWebResource(RequestData requestData) {
        Invocation.Builder wbr;
        WebTarget target;
        boolean isHeaderValueExists = false;
        client.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION,true);
        target = client.target(requestData.getUrl());
        //adding OAuth authorization header to the HTTP request, if no authorization header is already present.
        //addFilters(resource);
        wbr = target.request();

        //Set request headers
        if (requestData.getHeaders() != null) {
            isHeaderValueExists = isRequestDataContainsHeaderValue(requestData, "application/json");

            for (String headK : requestData.getHeaders().keySet()) {
                wbr = wbr.header(headK, requestData.getHeaders().get(headK));
            }
        }
        //Set request cookie
        if (requestData.getCookies() != null) {
            for (NewCookie cookie : requestData.getCookies()) {
                wbr = wbr.cookie(cookie.toCookie());
            }
        }
        //Set Content type
        // TODO: 4/10/2019 Need to find more elegant solution
        if (requestData.getContentType() != null && !isHeaderValueExists) {
            wbr = wbr.accept(requestData.getContentType());
        }

        return wbr;
    }
    private boolean isRequestDataContainsHeaderValue(RequestData requestData, String value) {
        return requestData.getHeaders().values().stream().anyMatch(s -> s.contains(value));
    }

    /**
     * Can be overridden by a child class to specify a different content type
     *
     * @param contentType the content type you want.
     * @return signature method to use in Oauth request (Default to "application/xml")
     */
    public static MediaType getContentType(String contentType) {
        if (Pattern.compile(Pattern.quote("xml"), Pattern.CASE_INSENSITIVE).matcher(contentType).find()) {
            return MediaType.APPLICATION_XML_TYPE;
        } else if (Pattern.compile(Pattern.quote("/json"), Pattern.CASE_INSENSITIVE).matcher(contentType).find()) {
            return MediaType.APPLICATION_JSON_TYPE;
        } else if (Pattern.compile(Pattern.quote("atom"), Pattern.CASE_INSENSITIVE).matcher(contentType).find()) {
            return MediaType.APPLICATION_ATOM_XML_TYPE;
        } else if (Pattern.compile(Pattern.quote("urlencoded"), Pattern.CASE_INSENSITIVE).matcher(contentType).find()) {
            return MediaType.APPLICATION_FORM_URLENCODED_TYPE;
        } else if (Pattern.compile(Pattern.quote("octet"), Pattern.CASE_INSENSITIVE).matcher(contentType).find()) {
            return MediaType.APPLICATION_OCTET_STREAM_TYPE;
        } else if (Pattern.compile(Pattern.quote("svg"), Pattern.CASE_INSENSITIVE).matcher(contentType).find()) {
            return MediaType.APPLICATION_SVG_XML_TYPE;
        } else if (Pattern.compile(Pattern.quote("xhtml"), Pattern.CASE_INSENSITIVE).matcher(contentType).find()) {
            return MediaType.APPLICATION_XHTML_XML_TYPE;
        } else if (Pattern.compile(Pattern.quote("data"), Pattern.CASE_INSENSITIVE).matcher(contentType).find()) {
            return MediaType.MULTIPART_FORM_DATA_TYPE;
        } else if (Pattern.compile(Pattern.quote("html"), Pattern.CASE_INSENSITIVE).matcher(contentType).find()) {
            return MediaType.TEXT_HTML_TYPE;
        } else if (Pattern.compile(Pattern.quote("plain"), Pattern.CASE_INSENSITIVE).matcher(contentType).find()) {
            return MediaType.TEXT_PLAIN_TYPE;
        } else if (contentType.equalsIgnoreCase("") || contentType.equalsIgnoreCase("*/*") || Pattern.compile(Pattern.quote("wild"), Pattern.CASE_INSENSITIVE).matcher(contentType).find()) {
            return MediaType.WILDCARD_TYPE;
        }

        return MediaType.valueOf(contentType);
    }
}
