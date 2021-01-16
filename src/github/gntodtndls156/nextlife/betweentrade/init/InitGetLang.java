package github.gntodtndls156.nextlife.betweentrade.init;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class InitGetLang {
    public static Map<String, String> LANG = new HashMap<>();
    public InitGetLang(String lang) {
        Yaml yaml = new Yaml();
        File file = new File("plugins\\PlayerToPlayerTrade\\LANG\\" + lang + ".lang");

        try {
            LANG = yaml.load(new FileReader(file));
        } catch (FileNotFoundException exception) {
            File file1 = new File("plugins\\PlayerToPlayerTrade\\LANG");
            if (!file1.exists()) {
                file1.mkdir();
            }
            try {
                file.createNewFile();
                final String Context = "###\n" +
                        "# %s - Player's name\n" +
                        "# %d%s - MONEY's unit\n" +
                        "# %d - money\n" +
                        "###\n" +
                        "\n" +
                        "# TRADE\n" +
                        "\"PlayerProposePlayer\": \"%s에게 거래를 신청했습니다.\"\n" +
                        "\"DoNotRespond\": \"상대방이 거래에 응하지 않았습니다.\"\n" +
                        "\"PlayerDeny\": \"%s가 거래 신청에 대하여 거부하셨습니다.\"\n" +
                        "\"Wait\": \"잠시후 다시 시도 하시오\"\n" +
                        "\"GetMessage\": \"%s에게 거래 신청 받았습니다.\"\n" +
                        "\n" +
                        "\"Close\": \"닫기\"\n" +
                        "\"CloseL\": \"거래를 종료합니다.\"\n" +
                        "\n" +
                        "\"Lock\": \"거래 잠그기\"\n" +
                        "\"LockL\": \"이 재화로 거래하자고 제안합니다.\"\n" +
                        "\n" +
                        "\"Line\": \"경계선\"\n" +
                        "\n" +
                        "\"Accept\": \"거래 수락하기\"\n" +
                        "\"AcceptL1\": \"최종적으로 거래를 수락합니다.\"\n" +
                        "\"AcceptL2\": \"상대방도 수락할 경우 거래가 완료됩니다.\"\n" +
                        "\n" +
                        "\"Money\": \"돈 거래\"\n" +
                        "\"MoneyL\": \"돈을 얼만큼 주고 받을지 요구합니다.\"\n" +
                        "\n" +
                        "# MONEY\n" +
                        "\"unit\": \"C\"\n" +
                        "\n" +
                        "\"Add\": \"추가 \"\n" +
                        "\"AddL1\": \"클릭해서 추가합니다.\"\n" +
                        "\"AddL2\": \"현재 %d%s 로 설정된 상태입니다.\"\n" +
                        "\"AddL3\": \"거래 성사 이후 예상되는 잔액은 %d%s 입니다.\"\n" +
                        "\n" +
                        "\"Remove\": \"빼기 \"\n" +
                        "\"RemoveL1\": \"클릭해서 뺍니다.\"\n" +
                        "\"RemoveL2\": \"현재 %d%s 로 설정된 상태입니다.\"\n" +
                        "\"RemoveL3\": \"빼고 나서 거래 예정인 금액은 %d%s 입니다.\"\n" +
                        "\n" +
                        "\"Back\": \"뒤로가기\"\n" +
                        "\"BackL\": \"클릭해서 뒤로 갑니다.\"\n" +
                        "\n" +
                        "\"AddToTrade\": \"이 금액으로 거래하기\"\n" +
                        "\"AddToTradeL1\": \"클릭하면 %d%s 만큼 거래 테이블에 상정합니다.\"\n" +
                        "\"AddToTradeL3\": \"거래 성사 이후 예상되는 잔액은 %d%s 입니다.\"";
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(Context);
                fileWriter.flush();
                fileWriter.close();

                LANG = yaml.load(new FileReader(file));
            } catch (IOException exception1) {
                // TODO
            }
        }
    }
}
