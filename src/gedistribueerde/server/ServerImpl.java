package gedistribueerde.server;


import gedistribueerde.Client.Document;

import java.util.Date;

public class ServerImpl implements Server {
    @Override
    public void log(Document document) {
        Date date = new Date();
        System.out.println(date + ": document.getText() = " + document.getText());
    }

    @Override
    public Document create(String s) {
        Document document = new DocumentImpl();
        document.setText("---" + s + "---");
        return document;
    }

    @Override
    public void toUpper(Document document) {
        String text = document.getText();
        for(int i=0; i<text.length(); i++) {
            char c = text.charAt(i);
            c = Character.toUpperCase(c);
            document.setChar(i, c);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toLower(Document document) {
        String text = document.getText().toLowerCase();
        document.setText(text);
    }

    @Override
    public void type(Document document, String text) {
        for(int i=0; i<text.length(); i++) {
            char c = text.charAt(i);
            document.append(c);
            int randomTime = (int)(Math.random()*1000+200);
            try {
                Thread.sleep(randomTime);
            } catch (InterruptedException e) {
            }
        }
    }
}
