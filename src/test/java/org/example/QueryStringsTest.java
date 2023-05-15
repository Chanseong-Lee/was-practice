package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringsTest {
    @DisplayName("queryString 문자열로 일급객체를 생성한다.")
    @Test
    void createTest() {
        QueryStrings queryStrings = new QueryStrings("operand1=11&operator=*&operand2=55");
        assertThat(queryStrings).isNotNull();
    }
}
