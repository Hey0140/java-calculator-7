package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    @Test
    void 커스텀_구분자_사용() {
        //기본 테스트
        assertSimpleTest(() -> {
            run("//;\\n1");
            assertThat(output()).contains("결과 : 1");
        });
        //기본 테스트
        assertSimpleTest(() -> {
            run("1,2:3");
            assertThat(output()).contains("결과 : 6");
        });
        //두 자릿 수 이상의 숫자
        assertSimpleTest(() -> {
            run("//;\\n1;21;361");
            assertThat(output()).contains("결과 : 383");
        });
        //두 자릿 수 이상의 숫자
        assertSimpleTest(() -> {
            run("//;\\n4;30;200");
            assertThat(output()).contains("결과 : 234");
        });
        //구분자 ^로 변경
        assertSimpleTest(() -> {
            run("//^\\n3^7^9");
            assertThat(output()).contains("결과 : 19");
        });
        //구분자 @로 변경
        assertSimpleTest(() -> {
            run("//@\\n19@8");
            assertThat(output()).contains("결과 : 27");
        });
    }

    @Test
    void 예외_테스트() {
        //기본 예외
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("-1,2,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
        //소수 or .구분자가 아닌 문자 사용
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1.3:3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
        //커스텀 구분자 명시하는 패턴 사이 문자가 두개 이상
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//*&\\n1*&2*&3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
        //숫자 사이에 구분자가 아닌 문자
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//)\\n1*7)3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
        //0(양의 정수만 가능, .는 문자가 될 수 있어 소수 불가능)
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//#\\n0#30"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
