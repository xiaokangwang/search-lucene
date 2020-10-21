package org.kkdev.java.school.tcd.search.searcherproj.data.loader;

import org.kkdev.java.school.tcd.search.searcherproj.data.DotFile;
import org.kkdev.java.school.tcd.search.searcherproj.data.DotFileArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DotFileLoader {
    enum ParserStatus {
        AWaitingDot,
        AWaitingText,
    }

    public static DotFileArray LoadDotFile(File file) throws FileNotFoundException {
        DotFileArray dotFileArray = new DotFileArray();
        Scanner input = new Scanner(file);
        input.useDelimiter("\r?\n");
        DotFile df = new DotFile();

        ParserStatus Status = ParserStatus.AWaitingDot;
        String DotTagBuf = "";
        StringBuilder DotTextBuf = new StringBuilder();

        for (Scanner it = input; it.hasNext(); ) {
            String Line = it.next();
            if (Status == ParserStatus.AWaitingText) {
                if (Line.charAt(0) == '.'){
                    Status = ParserStatus.AWaitingDot;

                    if (df.getData().containsKey(DotTagBuf)) {
                        dotFileArray.getContent().add(df);
                        df = new DotFile();
                    }
                    df.getData().put(DotTagBuf,DotTextBuf.toString());

                    //DotTagBuf = "";
                    DotTextBuf = new StringBuilder();


                }else {
                    DotTextBuf.append(Line);
                    DotTextBuf.append(" ");
                }
            }
            if (Status == ParserStatus.AWaitingDot) {
                String[] s = Line.split(" ");
                switch (s.length) {
                    case 1:
                        if (DotTagBuf.equals(s[0]) ){
                           String Oldin = df.getData().get(DotTagBuf);
                            DotTextBuf = new StringBuilder(Oldin);
                            df.getData().remove(DotTagBuf);
                        }
                        DotTagBuf = s[0];
                        Status = ParserStatus.AWaitingText;
                        break;
                    case 2:
                        if (df.getData().containsKey(s[0])) {
                            dotFileArray.getContent().add(df);
                            df = new DotFile();
                        }
                        df.getData().put(s[0],s[1]);
                        DotTagBuf = "";
                }
            }


        }
        if (Status == ParserStatus.AWaitingText) {

            if (df.getData().containsKey(DotTagBuf)) {
                dotFileArray.getContent().add(df);
                df = new DotFile();
            }
            df.getData().put(DotTagBuf,DotTextBuf.toString());
        }
        dotFileArray.getContent().add(df);

        return dotFileArray;
    }
}
