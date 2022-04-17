package socketmedia.socketmediaplayer;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class client extends JFrame
{
    clientreceive cr;
    JPanel p;

    public void init()
    {
        clientreceive cr = new clientreceive();
    }

    public client()
    {
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel l = new JLabel("enter file name");
        l.setBounds(50, 30, 100, 50);
        getContentPane().add(l);

        final JTextField t = new JTextField();
        t.setBounds(160, 45, 100, 30);
        getContentPane().add(t);

        JButton b = new JButton("go");
        b.setBounds(145, 100, 50, 30);
        getContentPane().add(b);

        b.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                sendREQ(t.getText());

            }
        });

        JButton b1 = new JButton("play");
        b1.setBounds(145, 130, 70, 30);
        getContentPane().add(b1);

        b1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                mediaPlayer(cr.lrecFile.getAbsolutePath(),p);
            }
        });

        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createTitledBorder("Videoplayer"));
        p.setBounds(125, 200, 300, 300);
        getContentPane().add(p);

        setSize(600,600);
        setVisible(true);
    }

    protected void mediaPlayer(final String Path, final JPanel panel)
    {
        new Thread() {
            public void run() {
                try {
                    Player p = Manager.createRealizedPlayer(new File  (Path)
                            .toURL());
                    Component ctrlpanel = p.getControlPanelComponent();
                    Component player = p.getVisualComponent();
                    player.setBounds(10, 20, 300, 170);
                    ctrlpanel.setBounds(10, 191, 300, 20);
                    panel.add(player);

                    panel.add(ctrlpanel);
                    panel.repaint();
                    p.start();
                    System.out.println(" Player Started");
                } catch (NoPlayerException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (CannotRealizeException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();

    }

    protected void sendREQ(String text)
    {
        try {
            Socket soc = new Socket("localhost", 4576);
            ObjectOutputStream oos = new ObjectOutputStream(
                    soc.getOutputStream());
            oos.writeObject("REQ");
            oos.writeObject(text);

            System.out.println(text);

        }
        catch (UnknownHostException e)
        {

            e.printStackTrace();
        }
        catch (IOException e)
        {

            e.printStackTrace();
        }

    }

    public static void main(String args[])
    {
        new client();
    }
}

