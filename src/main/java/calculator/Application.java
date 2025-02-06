package calculator;

import camp.nextstep.edu.missionutils.Console;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        String string = Console.readLine();
        //정규표현식을 통해서 입력받은 문자열이 //(문자)\n의 형태인지 아닌지를 확인한다.
        //실제개행문자(\n)가 아니기때문에 문자열 백슬래시를 이스케이프를 진행
        String patternString = "^(?://(.{1})\\\\n(.*)|(?!//).*)$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(string);
        boolean isContain = matcher.matches();
        if (isContain){
            //구분자와 구분자를 제외한 수식 변수를 만든다
            String delimiter;
            String expression;
            //커스텀구분자를 제작한다면 커스텀구분자가 있는 그룹과 수식이 있는 그룹으로 나눈다.
            String customDelimiter = matcher.group(1);
            String rest = matcher.group(2);

            if (customDelimiter != null) {
                //커스텀 구분자가 존재한다면 해당 값을 구분자 변수에 넣어 준다.
                //수식이 있는 부분을 수식 변수에 넣어 준다.
                delimiter = customDelimiter;
                expression = rest;
            } else {
                //기본 구분자는 :와 ,인데 하나로 통일해주기 위해서 :로 통일해준다.
                delimiter = ":";
                expression = matcher.group(0);
                expression = expression.replace(",", delimiter);
            }
            //구분자로 수식을 나눈다.
            delimiter = Pattern.quote(delimiter);
            String[] splitExpression = expression.split(delimiter);
            int sum = 0;
            //소수를 표현하기 위한 .이 있으면 소수가 아니라 구분자로 인식될 것이기에 소수는 없다고 생각해도 된다.
            for (int i =0; i < splitExpression.length; i++){
                if (!splitExpression[i].matches("^[1-9][0-9]*$")) {
                    //양수에는 0이 없다. 0이 나와도 에러 처리를 진행한다.
                    throw new IllegalArgumentException("Charactor that isn't delimiter is in this expression");
                } else{
                    sum += Integer.parseInt(splitExpression[i]);
                }
            }
            System.out.println("결과 : "+sum);
        } else{
            throw new IllegalArgumentException("Not in rule. Can't caculate this expression");
        }
    }
}
