package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomWebApplicationServer {
    private final int port;

    //Thread Pool을 위한 클래스
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port.", port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServer] waiting for client.");
            // accept()가 실행되면 클라이언트를 기다리고 클라이언트가 들어오면 Socket 을 리턴
            while ((clientSocket = serverSocket.accept()) != null) {
                logger.info("[CustomWebApplicationServer] client connected");

                /*
                 * Step3 - TreadPool 을 적용하여 안정적인 서비스 구현
                 */
                executorService.execute(new ClientRequestHandler(clientSocket));

                /*
                 * Step2 - 사용자의 요청이 들어올 때마다 Tread 를 새로 생성해서 사용자 요청을 처리하도록 한다.
                 * tread 가 생성될때 마다 독립적인 stack 메모리를 할당 받기 때문에 자원낭비가 심하다.
                 * -> 성능 하락이 심해진다. -> ThreadPool 을 활용한다(생성된 쓰레드를 재활용).
                 */
//                new Thread(new ClientRequestHandler(clientSocket)).start();

                /*
                 * Step1 - 사용자 요청을 메인 Tread 가 처리하도록 한다.
                 * -> 메인쓰레드가 블락이 걸리면 다음요청을 처리할 수 없다.
                 */
//                try (
//                        InputStream in = clientSocket.getInputStream();
//                        OutputStream out = clientSocket.getOutputStream()) {
//                    // InputStream 을 reader 로 바꾼 이유는 line by line 으로 보고 싶어서
//                    BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
//                    DataOutputStream dos = new DataOutputStream(out);
////                    String line;
////                    while ((line = br.readLine()) != null) {
////                        // 출력되는 첫번째 라인이 request line
////                        logger.info(line);
////                    }
//                    HttpRequest httpRequest = new HttpRequest(br);
//                    if(httpRequest.isGetRequest() && httpRequest.matchPath("/calculate")){
//                        QueryStrings queryStrings = httpRequest.getQueryStrings();
//
//                        int operand1 = Integer.parseInt(queryStrings.getValue("operand1"));
//                        String operator = queryStrings.getValue("operator");
//                        int operand2 = Integer.parseInt(queryStrings.getValue("operand2"));
//
//                        int result = Calculator.calculate(new PositiveNumber(operand1),operator, new PositiveNumber(operand2));
//                        byte[] body = String.valueOf(result).getBytes();
//                        HttpResponse response = new HttpResponse(dos);
//                        response.response200Header("application/json", body.length);
//                        response.responseBody(body);
//                    }
//                }
            } //while end
        }
    }
}
