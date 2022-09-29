import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Window extends JFrame{
    private static final long serialVersionUID = 1L;
    Window(String title, int defx, int defy){
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                Main.end();
            }
        });
        this.setTitle(title);
        this.setSize(defx, defy);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        //Menu Bar
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("<html><span style='font-size:10px; font-family:Calibri;'>File</span></html>");
        JMenu menu1 = new JMenu("<html><span style='font-size:10px; font-family:Calibri;'>Edit</span></html>");
        JMenu menu2 = new JMenu("<html><span style='font-size:10px; font-family:Calibri;'>View</span></html>");
        //File Menu
        JMenuItem mi1 = new JMenuItem("<html><span style='font-size:10px; font-family:Calibri;'>Refresh</span></html>");
        mi1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                Main.dataUpdate();
                Main.reopenWin();
            }
        });
        JMenuItem mi2 = new JMenuItem("<html><span style='font-size:10px; font-family:Calibri;'>Search Customer</span></html>");
        mi2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                JFrame temp = new JFrame("Search");
                temp.setSize(250, 140);
                temp.setLocationRelativeTo(null);
                temp.setResizable(false);
                temp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                JLabel l = new JLabel("<html><span style='font-size:10px; font-family:Calibri;'>Name :</span></html>");
                l.setBounds(10, 20, 140, 20);
                temp.add(l);
                JTextField t = new JTextField();
                t.setBounds(80, 20, 140, 20);
                temp.add(t);
                JButton b = new JButton("<html><span style='font-size:10px; font-family:Calibri;'>Search</span></html>");
                b.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String s = t.getText();
                        if(!s.equals("")){
                            JFrame f = new JFrame("Search");
                            f.setSize(450, 600);
                            f.setLocationRelativeTo(null);
                            f.setResizable(true);
                            f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                            JPanel p = new JPanel();
                            f.add(new JScrollPane(p));
                            p.setLayout(new GridBagLayout());
                            GridBagConstraints g = new GridBagConstraints();
                            g.anchor = GridBagConstraints.PAGE_START;
                            g.fill = GridBagConstraints.HORIZONTAL;
                            g.gridx = 0;
                            g.gridy = 0;
                            p.add(new JLabel("<html><span style='font-size:24px; font-family:Calibri;'>Customers with " + s + " :</span></html>"), g);
                            g.gridy++;
                            for(Customer c : Main.customers){
                                if(c.getName().toLowerCase().contains(s.toLowerCase())){
                                    p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Customer " + c.getId() + " (" + c.getName() + ")</span></html>"), g);
                                    g.gridy++;
                                }
                            }
                            f.setVisible(true);
                        }
                        else{
                            JOptionPane.showMessageDialog(new JFrame(), "Invalid Input !", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                b.setBounds(10, 60, 210, 20);
                temp.add(b);
                temp.setLayout(null);
                temp.setVisible(true);
            }
        });
        //Edit Menu
        JMenuItem m1i1 = new JMenuItem("<html><span style='font-size:10px; font-family:Calibri;'>New  Customer</span></html>");
        m1i1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                JFrame temp = new JFrame("New");
                temp.setSize(250, 220);
                temp.setLocationRelativeTo(null);
                temp.setResizable(false);
                temp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                JLabel l1 = new JLabel("<html><span style='font-size:10px; font-family:Calibri;'>Name :</span></html>");
                l1.setBounds(10, 20, 140, 20);
                temp.add(l1);
                JTextField t1 = new JTextField();
                t1.setBounds(80, 20, 140, 20);
                temp.add(t1);
                JLabel l2 = new JLabel("<html><span style='font-size:10px; font-family:Calibri;'>Reserved Days :</span></html>");
                l2.setBounds(10, 60, 120, 20);
                temp.add(l2);
                JTextField t2 = new JTextField();
                t2.setBounds(140, 60, 80, 20);
                temp.add(t2);
                JLabel l3 = new JLabel("<html><span style='font-size:10px; font-family:Calibri;'>Room ID :</span></html>");
                l3.setBounds(10, 100, 120, 20);
                temp.add(l3);
                JTextField t3 = new JTextField();
                t3.setBounds(140, 100, 80, 20);
                temp.add(t3);
                JButton b = new JButton("<html><span style='font-size:10px; font-family:Calibri;'>Add</span></html>");
                b.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String s1 = t1.getText();
                        String s2 = t2.getText();
                        String s3 = t3.getText();
                        if(!s1.equals("") && s2.matches("-?\\d+") && Integer.parseInt(s2) > 0 && s3.matches("-?\\d+") && Room.FindRoomById(Integer.parseInt(s3)) != null){
                            if(Room.FindRoomById(Integer.parseInt(s3)).isReserved()){
                                JOptionPane.showMessageDialog(new JFrame(), "That room is already reserved !", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else{
                                int i;
                                for(i = 1; Customer.FindCustomerById(i) != null; i++);
                                Main.customers.add(new Customer(String.valueOf(i), "0", s3, s1, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))));
                                Customer.FindCustomerById(i).extendTime(Integer.parseInt(s2));
                                Room.FindRoomById(Integer.parseInt(s3)).reserve(i);
                                Main.dataUpdate();
                                temp.dispose();
                                Main.reopenWin();
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(new JFrame(), "Invalid Input !", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                b.setBounds(10, 140, 210, 20);
                temp.add(b);
                temp.setLayout(null);
                temp.setVisible(true);
            }
        });
        JMenuItem m1i2 = new JMenuItem("<html><span style='font-size:10px; font-family:Calibri;'>New  Room</span></html>");
        m1i2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                JFrame temp = new JFrame("New");
                temp.setSize(250, 180);
                temp.setLocationRelativeTo(null);
                temp.setResizable(false);
                temp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                JLabel l1 = new JLabel("<html><span style='font-size:10px; font-family:Calibri;'>Beds :</span></html>");
                l1.setBounds(10, 20, 120, 20);
                temp.add(l1);
                JTextField t1 = new JTextField();
                t1.setBounds(140, 20, 80, 20);
                temp.add(t1);
                JLabel l2 = new JLabel("<html><span style='font-size:10px; font-family:Calibri;'>Price Per Day :</span></html>");
                l2.setBounds(10, 60, 120, 20);
                temp.add(l2);
                JTextField t2 = new JTextField();
                t2.setBounds(140, 60, 80, 20);
                temp.add(t2);
                JButton b = new JButton("<html><span style='font-size:10px; font-family:Calibri;'>Add</span></html>");
                b.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String s1 = t1.getText();
                        String s2 = t2.getText();
                        if(s1.matches("-?\\d+") && s2.matches("-?\\d+") && Integer.parseInt(s1) > 0 && Integer.parseInt(s2) > 0){
                            int i;
                            for(i = 1; Room.FindRoomById(i) != null; i++);
                            Main.rooms.add(new Room(String.valueOf(i), s1, s2, "false", "0/0/0", "0"));
                            Main.dataUpdate();
                            temp.dispose();
                            Main.reopenWin();
                        }
                        else{
                            JOptionPane.showMessageDialog(new JFrame(), "Invalid Input !", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                b.setBounds(10, 100, 210, 20);
                temp.add(b);
                temp.setLayout(null);
                temp.setVisible(true);
            }
        });
        //View Menu
        JMenuItem m2i1 = new JMenuItem("<html><span style='font-size:10px; font-family:Calibri;'>Empty Rooms</span></html>");
        m2i1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                JFrame temp = new JFrame("Empty Rooms");
                temp.setSize(450, 600);
                temp.setLocationRelativeTo(null);
                temp.setResizable(true);
                temp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                JPanel p = new JPanel();
                temp.add(new JScrollPane(p));
                p.setLayout(new GridBagLayout());
                GridBagConstraints g = new GridBagConstraints();
                g.anchor = GridBagConstraints.PAGE_START;
                g.fill = GridBagConstraints.HORIZONTAL;
                g.gridx = 0;
                g.gridy = 0;
                p.add(new JLabel("<html><span style='font-size:24px; font-family:Calibri;'>Empty Rooms :</span></html>"), g);
                g.gridy++;
                for(Room r : Main.rooms){
                    if(!r.isReserved()){
                        p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Room " + r.getId() + " (" + r.getBeds() + " Beds )</span></html>"), g);
                        g.gridy++;
                    }
                }
                temp.setVisible(true);
            }
        });
        JMenuItem m2i2 = new JMenuItem("<html><span style='font-size:10px; font-family:Calibri;'>Expired Rooms</span></html>");
        m2i2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                JFrame temp = new JFrame("Expired Rooms");
                temp.setSize(450, 600);
                temp.setLocationRelativeTo(null);
                temp.setResizable(true);
                temp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                JPanel p = new JPanel();
                temp.add(new JScrollPane(p));
                p.setLayout(new GridBagLayout());
                GridBagConstraints g = new GridBagConstraints();
                g.anchor = GridBagConstraints.PAGE_START;
                g.fill = GridBagConstraints.HORIZONTAL;
                g.gridx = 0;
                g.gridy = 0;
                p.add(new JLabel("<html><span style='font-size:24px; font-family:Calibri;'>Expired Rooms :</span></html>"), g);
                g.gridy++;
                for(Room r : Main.rooms){
                    if(r.isReserved() && Customer.FindCustomerById(r.getReserverId()).isExpired()){
                        p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Room " + r.getId() + " (Customer " + Customer.FindCustomerById(r.getReserverId()).getId() + ")</span></html>"), g);
                        g.gridy++;
                    }
                }
                temp.setVisible(true);
            }
        });
        JMenuItem m2i3 = new JMenuItem("<html><span style='font-size:10px; font-family:Calibri;'>Expired Customers</span></html>");
        m2i3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                JFrame temp = new JFrame("Expired Customers");
                temp.setSize(450, 600);
                temp.setLocationRelativeTo(null);
                temp.setResizable(true);
                temp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                JPanel p = new JPanel();
                temp.add(new JScrollPane(p));
                p.setLayout(new GridBagLayout());
                GridBagConstraints g = new GridBagConstraints();
                g.anchor = GridBagConstraints.PAGE_START;
                g.fill = GridBagConstraints.HORIZONTAL;
                g.gridx = 0;
                g.gridy = 0;
                p.add(new JLabel("<html><span style='font-size:24px; font-family:Calibri;'>Expired Customers :</span></html>"), g);
                g.gridy++;
                for(Customer c : Main.customers){
                    if(c.isExpired()){
                        p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Customer " + c.getId() + " (" + c.getName() + ")</span></html>"), g);
                        g.gridy++;
                    }
                }
                temp.setVisible(true);
            }
        });
        JMenuItem m2i4 = new JMenuItem("<html><span style='font-size:10px; font-family:Calibri;'>Earnings Report</span></html>");
        m2i4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                JFrame temp = new JFrame("Earnings Report");
                temp.setSize(450, 650);
                temp.setLocationRelativeTo(null);
                temp.setResizable(true);
                temp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                JPanel p = new JPanel();
                temp.add(new JScrollPane(p));
                p.setLayout(new GridBagLayout());
                GridBagConstraints g = new GridBagConstraints();
                g.anchor = GridBagConstraints.PAGE_START;
                g.fill = GridBagConstraints.HORIZONTAL;
                g.gridx = 0;
                g.gridy = 0;
                p.add(new JLabel("<html><span style='font-size:24px; font-family:Calibri;'>Today :</span></html>"), g);
                g.gridy++;
                p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Maximum Earnings : " + Room.getTotalEarn() + " " + Main.unit + "</span></html>"), g);
                g.gridy++;
                p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Earnings : " + Room.getTodayEarn() + " " + Main.unit + "</span></html>"), g);
                g.gridy++;
                p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Lost Earnings : " + (Room.getTotalEarn() - Room.getTodayEarn()) + " " + Main.unit + "</span></html>"), g);
                g.gridy++;
                p.add(new JLabel(" "), g);
                g.gridy++;
                p.add(new JLabel("<html><span style='font-size:24px; font-family:Calibri;'>Last Week :</span></html>"), g);
                g.gridy++;
                if(Main.earns.size() >= 7){
                    p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Maximum Earnings : " + Room.getWeekTotalEarn() + " " + Main.unit + "</span></html>"), g);
                    g.gridy++;
                    p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Earnings : " + Room.getWeekEarn() + " " + Main.unit + "</span></html>"), g);
                    g.gridy++;
                    p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Lost Earnings : " + (Room.getWeekTotalEarn() - Room.getWeekEarn()) + " " + Main.unit + "</span></html>"), g);
                    g.gridy++;
                }
                else{
                    p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Not enough days have passed !</span></html>"), g);
                    g.gridy++;
                }
                p.add(new JLabel(" "), g);
                g.gridy++;
                p.add(new JLabel("<html><span style='font-size:24px; font-family:Calibri;'>Last Month :</span></html>"), g);
                g.gridy++;
                if(Main.earns.size() >= 30){
                    p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Maximum Earnings : " + Room.getMonthTotalEarn() + " " + Main.unit + "</span></html>"), g);
                    g.gridy++;
                    p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Earnings : " + Room.getMonthEarn() + " " + Main.unit + "</span></html>"), g);
                    g.gridy++;
                    p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Lost Earnings : " + (Room.getMonthTotalEarn() - Room.getMonthEarn()) + " " + Main.unit + "</span></html>"), g);
                    g.gridy++;
                }
                else{
                    p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Not enough days have passed !</span></html>"), g);
                    g.gridy++;
                }
                p.add(new JLabel(" "), g);
                g.gridy++;
                p.add(new JLabel("<html><span style='font-size:24px; font-family:Calibri;'>Last Year :</span></html>"), g);
                g.gridy++;
                if(Main.earns.size() >= 365){
                    p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Maximum Earnings : " + Room.getYearTotalEarn() + " " + Main.unit + "</span></html>"), g);
                    g.gridy++;
                    p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Earnings : " + Room.getYearEarn() + " " + Main.unit + "</span></html>"), g);
                    g.gridy++;
                    p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Lost Earnings : " + (Room.getYearTotalEarn() - Room.getYearEarn()) + " " + Main.unit + "</span></html>"), g);
                    g.gridy++;
                }
                else{
                    p.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Not enough days have passed !</span></html>"), g);
                    g.gridy++;
                }
                temp.setVisible(true);
            }
        });
        menu.add(mi1);
        menu.add(mi2);
        menu1.add(m1i1);
        menu1.add(m1i2);
        menu2.add(m2i1);
        menu2.add(m2i2);
        menu2.add(m2i3);
        menu2.add(m2i4);
        menubar.add(menu);
        menubar.add(menu1);
        menubar.add(menu2);
        this.setJMenuBar(menubar); 
        //Main Panel
        JPanel panel = new JPanel();
        this.add(panel);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.gridy = 0;
        //Customers Panel in the left
        gbc.weightx = 0.65;
        gbc.gridx = 0;
        JPanel cpanel = new JPanel();
        panel.add(new JScrollPane(cpanel), gbc);
        //Rooms Panel in the left
        gbc.weightx = 1;
        gbc.gridx = 1;
        JPanel rpanel = new JPanel();
        panel.add(new JScrollPane(rpanel), gbc);
        //Loading Panels
        this.cPanel(cpanel);
        this.rPanel(rpanel);
        //Done !
        this.setVisible(true);
    }
    //Customers Panel
    private void cPanel(JPanel cpanel){
        cpanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.15;
        gbc.gridy = 0;
        //Left Padding
        gbc.gridx = 0;
        cpanel.add(new JPanel(), gbc);
        //Right Padding
        gbc.gridx = 3;
        cpanel.add(new JPanel(), gbc);
        //Title
        gbc.weightx = 1;
        gbc.gridx = 1;
        cpanel.add(new JLabel("<html><span style='font-size:24px; font-family:Calibri;'>Customers :</span></html>"), gbc);
        gbc.gridy++;
        //Customers
        for(Customer c : Main.customers){
            cpanel.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Customer " + c.getId() + " :</span></html>"), gbc);
            gbc.gridy++;
            cpanel.add(new JLabel("<html><span style='font-size:12px; font-family:Calibri;'>Name : " + c.getName() + "</span></html>"), gbc);
            gbc.gridx++;
            JButton button1 = new JButton("<html><span style='font-size:12px; font-family:Calibri;'>Modify</span></html>");
            button1.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame temp = new JFrame("Modify");
                    temp.setSize(250, 180);
                    temp.setLocationRelativeTo(null);
                    temp.setResizable(false);
                    temp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    JLabel l1 = new JLabel("<html><span style='font-size:10px; font-family:Calibri;'>Name :</span></html>");
                    l1.setBounds(10, 20, 140, 20);
                    temp.add(l1);
                    JTextField t1 = new JTextField(c.getName());
                    t1.setBounds(80, 20, 140, 20);
                    temp.add(t1);
                    JLabel l2 = new JLabel("<html><span style='font-size:10px; font-family:Calibri;'>Add Days (+/-) :</span></html>");
                    l2.setBounds(10, 60, 140, 20);
                    temp.add(l2);
                    JTextField t2 = new JTextField("0");
                    t2.setBounds(140, 60, 80, 20);
                    temp.add(t2);
                    JButton b = new JButton("<html><span style='font-size:10px; font-family:Calibri;'>Modify</span></html>");
                    b.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String s1 = new String(t1.getText());
                            String s2 = new String(t2.getText());
                            if(!s1.equals("") && s2.matches("-?\\d+")){ //Its ok to enter negative numbers since its counted as cancellation
                                c.setName(s1);
                                c.extendTime(Integer.parseInt(s2));
                                Room.FindRoomById(c.getRoomId()).setEmptyDate(c.getExpireDate());
                                Main.dataUpdate();
                                temp.dispose();
                                Main.reopenWin();
                            }
                            else{
                                JOptionPane.showMessageDialog(new JFrame(), "Invalid Input !", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                    b.setBounds(10, 100, 210, 20);
                    temp.add(b);
                    temp.setLayout(null);
                    temp.setVisible(true);
                }
            });
            cpanel.add(button1, gbc);
            gbc.gridx--;
            gbc.gridy++;
            cpanel.add(new JLabel("<html><span style='font-size:12px; font-family:Calibri;'>Residence : Room " + c.getRoomId() + "</span></html>"), gbc);
            gbc.gridy++;
            cpanel.add(new JLabel("<html><span style='font-size:12px; font-family:Calibri;'>Dept : " + c.getDept() + " " + Main.unit + "</span></html>"), gbc);
            gbc.gridx++;
            JButton button2 = new JButton("<html><span style='font-size:12px; font-family:Calibri;'>Checkout</span></html>");
            button2.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame temp = new JFrame("Checkout");
                    temp.setSize(240, 130);
                    temp.setLocationRelativeTo(null);
                    temp.setResizable(false);
                    temp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    JLabel l = new JLabel("<html><span style='font-size:10px; font-family:Calibri;'>Please Withdraw " + c.getDept() + " " + Main.unit + " </span></html>");
                    l.setBounds(10, 20, 200, 30);
                    temp.add(l);
                    JButton b = new JButton("<html><span style='font-size:10px; font-family:Calibri;'>Complete</span></html>");
                    b.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            temp.dispose();
                            Room.FindRoomById(c.getRoomId()).clear();
                            Main.customers.remove(c);
                            Main.dataUpdate();
                            Main.reopenWin();
                        }
                    });
                    b.setBounds(10, 60, 200, 20);
                    temp.add(b);
                    temp.setLayout(null);
                    temp.setVisible(true);
                }
            });
            cpanel.add(button2, gbc);
            gbc.gridx--;
            gbc.gridy++;
            if(c.isExpired()){
                cpanel.add(new JLabel("<html><span style='font-size:12px; font-family:Calibri;'>Expire Date : " + c.getExpireDate() + " (Expired)</span></html>"), gbc);
            }
            else{
                cpanel.add(new JLabel("<html><span style='font-size:12px; font-family:Calibri;'>Expire Date : " + c.getExpireDate() + "</span></html>"), gbc);
            }
            gbc.gridy++;
            cpanel.add(new JLabel(" "), gbc);
            gbc.gridy++;
        }
    }
    //Rooms Panel
    private void rPanel(JPanel rpanel){
        rpanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.15;
        gbc.gridy = 0;
        //Left Padding
        gbc.gridx = 0;
        rpanel.add(new JPanel(), gbc);
        //Right Padding
        gbc.gridx = 3;
        rpanel.add(new JPanel(), gbc);
        //Title
        gbc.weightx = 1;
        gbc.gridx = 1;
        rpanel.add(new JLabel("<html><span style='font-size:24px; font-family:Calibri;'>Rooms :</span></html>"), gbc);
        gbc.gridy++;
        //Rooms
        for(Room r : Main.rooms){
            rpanel.add(new JLabel("<html><span style='font-size:16px; font-family:Calibri;'>Room " + r.getId() + " :</span></html>"), gbc);
            gbc.gridx++;
            JButton button1 = new JButton("<html><span style='font-size:12px; font-family:Calibri;'>Modify</span></html>");
            button1.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame temp = new JFrame("Modify");
                    temp.setSize(250, 180);
                    temp.setLocationRelativeTo(null);
                    temp.setResizable(false);
                    temp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    JLabel l1 = new JLabel("<html><span style='font-size:10px; font-family:Calibri;'>Beds :</span></html>");
                    l1.setBounds(10, 20, 120, 20);
                    temp.add(l1);
                    JTextField t1 = new JTextField(String.valueOf(r.getBeds()));
                    t1.setBounds(140, 20, 80, 20);
                    temp.add(t1);
                    JLabel l2 = new JLabel("<html><span style='font-size:10px; font-family:Calibri;'>Price :</span></html>");
                    l2.setBounds(10, 60, 120, 20);
                    temp.add(l2);
                    JTextField t2 = new JTextField(String.valueOf(r.getPrice()));
                    t2.setBounds(140, 60, 80, 20);
                    temp.add(t2);
                    JButton b = new JButton("<html><span style='font-size:10px; font-family:Calibri;'>Modify</span></html>");
                    b.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String s1 = new String(t1.getText());
                            String s2 = new String(t2.getText());
                            if(s1.matches("-?\\d+") && s2.matches("-?\\d+") && Integer.parseInt(s1) > 0 && Integer.parseInt(s2) > 0){
                                temp.dispose();
                                r.setBeds(Integer.parseInt(s1));
                                r.setPrice(Integer.parseInt(s2));
                                Main.dataUpdate();
                                Main.reopenWin();
                            }
                            else{
                                JOptionPane.showMessageDialog(new JFrame(), "Invalid Input !", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                    b.setBounds(10, 100, 210, 20);
                    temp.add(b);
                    temp.setLayout(null);
                    temp.setVisible(true);
                }
            });
            rpanel.add(button1, gbc);
            gbc.gridx--;
            gbc.gridy++;
            rpanel.add(new JLabel("<html><span style='font-size:12px; font-family:Calibri;'>Beds : " + r.getBeds() + "</span></html>"), gbc);
            gbc.gridy++;
            rpanel.add(new JLabel("<html><span style='font-size:12px; font-family:Calibri;'>Price per day : " + r.getPrice() + " " + Main.unit + "</span></html>"), gbc);
            gbc.gridx++;
            JButton button2 = new JButton("<html><span style='font-size:12px; font-family:Calibri;'>Remove</span></html>");
            button2.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame temp = new JFrame("Remove");
                    temp.setSize(250, 180);
                    temp.setLocationRelativeTo(null);
                    temp.setResizable(false);
                    temp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    JLabel l = new JLabel("<html><span style='font-size:10px; font-family:Calibri;'>Are you sure ?</span></html>");
                    l.setBounds(10, 20, 140, 20);
                    temp.add(l);
                    JButton b = new JButton("<html><span style='font-size:10px; font-family:Calibri;'>Remove</span></html>");
                    b.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            temp.dispose();
                            Main.rooms.remove(r);
                            Main.dataUpdate();
                            Main.reopenWin();
                        }
                    });
                    b.setBounds(10, 60, 210, 20);
                    temp.add(b);
                    temp.setLayout(null);
                    temp.setVisible(true);
                }
            });
            rpanel.add(button2, gbc);
            gbc.gridx--;
            gbc.gridy++;
            if(r.isReserved()){
                if(Customer.FindCustomerById(r.getReserverId()).isExpired()){
                    rpanel.add(new JLabel("<html><span style='font-size:12px; font-family:Calibri;'>Room is reserved until " + r.emptyDate() + " (Expired) by customer " +  r.getReserverId() + " (" + Customer.FindCustomerById(r.getReserverId()).getName() + ") !</span></html>"), gbc);
                }
                else{
                    rpanel.add(new JLabel("<html><span style='font-size:12px; font-family:Calibri;'>Room is reserved until " + r.emptyDate() + " by customer " +  r.getReserverId() + " (" + Customer.FindCustomerById(r.getReserverId()).getName() + ") !</span></html>"), gbc);
                }
                button1.setEnabled(false);
                button2.setEnabled(false);
            }
            else{
                rpanel.add(new JLabel("<html><span style='font-size:12px; font-family:Calibri;'>Room is empty !</span></html>"), gbc);
            }
            gbc.gridy++;
            rpanel.add(new JLabel(" "), gbc);
            gbc.gridy++;
        }
    }
}