package practice.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import practice.springmvc.basic.HelloData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {
    /**
     * 戻り値がなく、このようにレスポンスに値を直接入れる場合、ビューの検索は行われません
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);
        response.getWriter().write("ok");
    }


    /**
     * @RequestParamの使用 - パラメータ名でバインディング
     * @ResponseBodyの追加 - ビューの検索を無視し、HTTPメッセージボディに直接該当内容を入力
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    /**
     * @RequestParamの使用 HTTPパラメータ名が変数名と同じ場合は@RequestParam(name="xx")を省略可能
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParamの使用 String、intなどの単純型であれば@RequestParamも省略可能
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }


    /**
     * @RequestParam.required
     * /request-param-required -> usernameがないため例外
     *
     * 注意！
     * /request-param-required?username= -> 空文字で通過
     *
     * 注意！
     * /request-param-required
     * int age -> nullをint型に入力することはできないため、Integer型に変更する必要がある(または次に説明するdefaultValueを使用)
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam
     * - defaultValueの使用
     *
     * 参考: defaultValueは空文字の場合にも適用される
     * /request-param-default?username=
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam Map, MultiValueMap
     * Map(key=value)
     * MultiValueMap(key=[value1, value2, ...]) ex) (key=userIds, value=[id1, id2])
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"),
                paramMap.get("age"));
        return "ok";
    }

    /**
     * @ModelAttribute の使用
     * 参考: model.addAttribute(helloData) コードを一緒に適用する, 後ろのmodelを使う時に説明
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(),
                helloData.getAge());
        return "ok";
    }

    /**
     * @ModelAttribute 省略可能
     * String, int みたいな単純タイプ = @RequestParam
     * argument resolverに指定したタイプ外 = @ModelAttribute */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(),
                helloData.getAge());
        return "ok";
    }
}
