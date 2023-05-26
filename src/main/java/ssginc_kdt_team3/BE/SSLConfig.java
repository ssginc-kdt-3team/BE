package ssginc_kdt_team3.BE;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.*;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.concurrent.TimeUnit;

@Configuration
public class SSLConfig {

    @Value("${server.ssl.trust-store}")
    private String certPath;

    @Value("${server.ssl.trust-store-password}")
    private String certPassword;

    @Value("${server.ssl.key-store-type}")
    private String certType;

    @Bean
    public void createOkHttpClientBuilder(Request request) throws Exception {

        //인증서 가져오기
        KeyStore keyStore = KeyStore.getInstance(certType); //인증서 타입

        InputStream certInputStream = Files.newInputStream(Paths.get(certPath));
        keyStore.load(certInputStream, certPassword.toCharArray());

        //key manager 초기화
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, certPassword.toCharArray());

        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

        //Trust Manager 초기화
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        //SSLContext 초기화
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagers, trustManagerFactory.getTrustManagers(), null);

        // SSLParameters 설정 (암호화 알고리즘, 프로토콜 버전 등)
        SSLParameters sslParameters = sslContext.getSupportedSSLParameters();

        // SSL 엔진 설정
        SSLEngine sslEngine = sslContext.createSSLEngine();
        sslEngine.setSSLParameters(sslParameters);

        // SSL 소켓 설정
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        SSLSocket sslSocket = (SSLSocket) sslContext.getSocketFactory().createSocket();
        sslSocket.setSSLParameters(sslParameters);

        //OkHttpClient.Builder 객체 생성
        OkHttpClient.Builder CreateBuilder = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustManagerFactory.getTrustManagers()[0])
                .hostnameVerifier((hostname, session) -> true)
                .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustManagerFactory.getTrustManagers()[0])
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);

        OkHttpClient client = CreateBuilder.build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            String responseBody = response.body().toString();
            System.out.println(responseBody);
        } else {
            throw new Exception("error!");
        }
    }

}
