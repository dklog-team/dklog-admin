package kr.dklog.admin.dklogadmin.common.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Map;

@Component
public class NcpSmsUtil {

    @Value("${sms.type}")
    private String type;

    @Value("${sms.from}")
    private String from;

    @Value("${ncp.access-key-id}")
    private String ncpAccessKey;

    @Value("${ncp.secret-key}")
    private String ncpSecretKey;

    @Value("${ncp.notification.service-id}")
    private String ncpServiceId;

    private String ncpApiUrl = "https://sens.apigw.ntruss.com";

    public Map getSmsRequestData(String requestId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        Long timestamp = new Timestamp(System.currentTimeMillis()).getTime();

        WebClient webClient = WebClient.builder()
                .baseUrl(ncpApiUrl)
                .defaultHeader("x-ncp-apigw-timestamp", String.valueOf(timestamp))
                .defaultHeader("x-ncp-iam-access-key", ncpAccessKey)
                .defaultHeader("x-ncp-apigw-signature-v2", makeSignature(String.valueOf(timestamp), "/sms/v2/services/" + ncpServiceId + "/messages?requestId=" + requestId, HttpMethod.GET))
                .build();

        Map<String, Object> result = webClient.get()
                .uri("/sms/v2/services/" + ncpServiceId + "/messages?requestId=" + requestId)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return result;
    }

    public Map getSmsResultData(String messageId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        Long timestamp = new Timestamp(System.currentTimeMillis()).getTime();

        WebClient webClient = WebClient.builder()
                .baseUrl(ncpApiUrl)
                .defaultHeader("x-ncp-apigw-timestamp", String.valueOf(timestamp))
                .defaultHeader("x-ncp-iam-access-key", ncpAccessKey)
                .defaultHeader("x-ncp-apigw-signature-v2", makeSignature(String.valueOf(timestamp), "/sms/v2/services/" + ncpServiceId + "/messages/" + messageId, HttpMethod.GET))
                .build();

        Map<String, Object> result = webClient.get()
                .uri("/sms/v2/services/" + ncpServiceId + "/messages/" + messageId)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return result;
    }

    private String makeSignature(String timestampString, String urlString, HttpMethod httpMethod) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

        String space = " ";
        String newLine = "\n";
        String method = httpMethod.name();
        String accessKey = ncpAccessKey;
        String secretKey = ncpSecretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(urlString)
                .append(newLine)
                .append(timestampString)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));

        return Base64.encodeBase64String(rawHmac);
    }
}
