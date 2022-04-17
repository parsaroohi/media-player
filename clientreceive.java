package socketmedia.socketmediaplayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;


public class clientreceive
{
    File lrecFile;
    Socket soc;
    ServerSocket serSoc;
    ObjectInputStream ois;

    clientreceive()
    {
        receive();
    }

    private void receive()
    {
        try
        {
            System.out.println("Inside clientreceive");
            serSoc = new ServerSocket(4576);

            while (true)
            {
                soc = serSoc.accept();
                ois = new ObjectInputStream(soc.getInputStream());
                String str = (String) ois.readObject();
                checkStatus(str);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void checkStatus(String str)
    {
        try
        {
            if (str.equals("REP"))
            {
                byte[] file = (byte[]) ois.readObject();
                String filename = (String) ois.readObject();

                String scn = (String) ois.readObject();

                lrecFile = new File("RecFiles/" + scn + "" + filename);

                FileOutputStream fos = new FileOutputStream(lrecFile);
                fos.write(file);
                fos.close();



                JOptionPane.showMessageDialog(null, "Last Received File is:"
                        + lrecFile.getAbsolutePath());

            }
            else if (str.equals("NoFile")) {
                JOptionPane.showMessageDialog(null,
                        "The User Requested File Not Found.");

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
