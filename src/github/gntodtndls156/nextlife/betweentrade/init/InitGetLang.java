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
                        "\"Line1\": \"경계선\"\n" +
                        "\n" +
                        "\"Accept\": \"거래 수락하기\"\n" +
                        "\"AcceptL\": |\n" +
                        "  최종적으로 거래를 수락합니다.\n" +
                        "  상대방도 수락할 경우 거래가 완료됩니다.\n" +
                        "\n" +
                        "\"Line2\": \"경계선\"\n" +
                        "\"Money\": \"돈 거래\"\n" +
                        "\"MoneyL\": \"돈을 얼만큼 주고 받을지 요구합니다.\"\n" +
                        "\n" +
                        "# MONEY\n" +
                        "\"unit\": \"Coins\"\n" +
                        "\n" +
                        "\"Add\": \"Add\"\n" +
                        "\"AddL1\": \"Click to add more\"\n" +
                        "\"AddL2\": \"%d%s will be moved in you Inventory\"\n" +
                        "\"AddL3\": \"After moving, you balance is %d%s\"\n" +
                        "\n" +
                        "\"Remove\": \"Remove\"\n" +
                        "\"RemoveL1\": \"Click to remove more\"\n" +
                        "\"RemoveL2\": \"Click to remove %d%s to this trade\"\n" +
                        "\"RemoveL3\": \"Your balance afterwards will be %d%s\"\n" +
                        "\n" +
                        "\"Back\": \"Back\"\n" +
                        "\"BackL\": \"Click to go back\"\n" +
                        "\n" +
                        "\"ShowMyMoney\": \"Type in Value\"\n" +
                        "\"ShowMyMoneyL\": \"Click to type in an exact value\"\n" +
                        "\n" +
                        "\"AddToTrade\": \"Add to Trade\"\n" +
                        "\"AddToTradeL1\": \"Click To add %d%s to this trade\"\n" +
                        "\"AddToTradeL3\": \"Your balance afterwards will be %d%s\"";
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
