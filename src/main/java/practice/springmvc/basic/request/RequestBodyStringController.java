package practice.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream,
                StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        response.getWriter().write("ok");
    }

    /**
     * InputStream(Reader): HTTP リクエストメッセージをBodyの内容を直接照会
     * OutputStream(Writer): HTTP リクエストメッセージをBodyに直接結果出力
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    /**
     * HttpEntity: HTTPヘッダー、ボディ情報を便利に参照
     * - メッセージボディ情報を直接参照(@RequestParamなし, @ModelAttributeなし)
     * - HttpMessageConverter使用 -> StringHttpMessageConverter適用
     *
     * 応答でのHttpEntityの使用も可能
     * - メッセージボディ情報を直接返す（ビューの参照なし）
     * - HttpMessageConverter使用 -> StringHttpMessageConverter適用
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);
        return new HttpEntity<>("ok");
    }

    /**
     * @RequestBody
     * - メッセージボディ情報を直接参照（@RequestParamなし、@ModelAttributeなし）
     * - HttpMessageConverter使用 -> StringHttpMessageConverter適用
     * @ResponseBody
     * - メッセージボディ情報を直接返却（ビュー参照なし）
     * - HttpMessageConverter使用 -> StringHttpMessageConverter適用
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {
        log.info("messageBody={}", messageBody);
        return "ok";
    }
}
