package test;

import bean.Node;

import java.util.ArrayList;

public class main {

    public static void main(String[] args){

        WordAnalyze analyze = new WordAnalyze();
        ArrayList<Node> arrayList = analyze.strExecute("5-2*2-2");

        GrammarAnalyze g =new GrammarAnalyze(arrayList);
        if (arrayList != null){
            g.program();
        }
    }
}
