package Electricity.Billing.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Random;

public class ElectricityBillingSystem {

    // database Connection Class
    static class Conn {
        Connection c;
        Statement s;

        public Conn() {
            try {
                // setup the connection to the database
                Class.forName("com.mysql.cj.jdbc.Driver");
                c = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricity_billing_system", "root", "rootroot");
                s = c.createStatement();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Login Page Class
    static class Login extends JFrame implements ActionListener {
        JButton login, cancel, signup;
        JTextField username, password;
        Choice logginin;

        Login() {
            super("Login Page");
            getContentPane().setBackground(Color.WHITE);
            setLayout(null);

            JLabel lblusername = new JLabel("Username");
            lblusername.setBounds(300, 20, 100, 20);
            add(lblusername);

            username = new JTextField();
            username.setBounds(400, 20, 150, 20);
            add(username);

            JLabel lblpassword = new JLabel("Password");
            lblpassword.setBounds(300, 60, 100, 20);
            add(lblpassword);

            password = new JTextField();
            password.setBounds(400, 60, 150, 20);
            add(password);

            JLabel loggininas = new JLabel("Loggin in as");
            loggininas.setBounds(300, 100, 100, 20);
            add(loggininas);

            logginin = new Choice();
            logginin.add("Admin");
            logginin.add("Customer");
            logginin.setBounds(400, 100, 150, 20);
            add(logginin);

            login = new JButton("Login");
            login.setBounds(330, 160, 100, 20);
            login.addActionListener(this);
            add(login);

            cancel = new JButton("Cancel");
            cancel.setBounds(450, 160, 100, 20);
            cancel.addActionListener(this);
            add(cancel);

            signup = new JButton("Signup");
            signup.setBounds(380, 200, 100, 20);
            signup.addActionListener(this);
            add(signup);

            setSize(640, 300);
            setLocation(400, 200);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == login) {
                String susername = username.getText();
                String spassword = password.getText();
                String user = logginin.getSelectedItem();
                try {
                    Conn c = new Conn();
                    String query = "Select * from login where username = '"+susername+"' and password = '"+spassword+"' and usr = '"+user+"'";
                    ResultSet rs = c.s.executeQuery(query);
                    if (rs.next()) {
                        String meter = rs.getString("meter_no");
                        setVisible(false);
                        new Project(user, meter);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Login");
                        username.setText("");
                        password.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (ae.getSource() == cancel) {
                setVisible(false);
            } else if (ae.getSource() == signup) {
                setVisible(false);
                new NewCustomer();
            }
        }

        public static void main(String[] args) {
            // start the application by showing the login page
            new Login();
        }
    }

    // New Customer Registration Class
    static class NewCustomer extends JFrame implements ActionListener {
        JTextField tfname, tfaddress, tfstate, tfcity, tfemail, tfphone;
        JButton next, cancel;
        JLabel lblmeter;

        NewCustomer() {
            setSize(700, 500);
            setLocation(400, 200);
            JPanel p = new JPanel();
            p.setLayout(null);
            p.setBackground(new Color(173, 216, 230));
            add(p);

            JLabel heading = new JLabel("New Customer");
            heading.setBounds(180, 10, 200, 25);
            heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
            p.add(heading);

            JLabel lblname = new JLabel("Customer Name");
            lblname.setBounds(100, 80, 110, 20);
            p.add(lblname);

            tfname = new JTextField();
            tfname.setBounds(240, 80, 200, 20);
            p.add(tfname);

            JLabel lblmeterno = new JLabel("Meter Number");
            lblmeterno.setBounds(100, 120, 100, 20);
            p.add(lblmeterno);

            lblmeter = new JLabel("");
            lblmeter.setBounds(240, 120, 100, 20);
            p.add(lblmeter);

            Random ran = new Random();
            long number = ran.nextLong() % 1000000;
            lblmeter.setText("" + Math.abs(number));

            JLabel lbladdress = new JLabel("Address");
            lbladdress.setBounds(100, 160, 100, 20);
            p.add(lbladdress);

            tfaddress = new JTextField();
            tfaddress.setBounds(240, 160, 200, 20);
            p.add(tfaddress);

            JLabel lblcity = new JLabel("City");
            lblcity.setBounds(100, 200, 100, 20);
            p.add(lblcity);

            tfcity = new JTextField();
            tfcity.setBounds(240, 200, 200, 20);
            p.add(tfcity);

            JLabel lblstate = new JLabel("State");
            lblstate.setBounds(100, 240, 100, 20);
            p.add(lblstate);

            tfstate = new JTextField();
            tfstate.setBounds(240, 240, 200, 20);
            p.add(tfstate);

            JLabel lblemail = new JLabel("Email");
            lblemail.setBounds(100, 280, 100, 20);
            p.add(lblemail);

            tfemail = new JTextField();
            tfemail.setBounds(240, 280, 200, 20);
            p.add(tfemail);

            JLabel lblphone = new JLabel("Phone Number");
            lblphone.setBounds(100, 320, 100, 20);
            p.add(lblphone);

            tfphone = new JTextField();
            tfphone.setBounds(240, 320, 200, 20);
            p.add(tfphone);

            next = new JButton("Next");
            next.setBounds(120, 390, 100, 25);
            next.addActionListener(this);
            p.add(next);

            cancel = new JButton("Cancel");
            cancel.setBounds(250, 390, 100, 25);
            cancel.addActionListener(this);
            p.add(cancel);

            setLayout(new BorderLayout());
            add(p, "Center");

            setVisible(true);
        }

        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == next) {
                String name = tfname.getText();
                String meter = lblmeter.getText();
                String address = tfaddress.getText();
                String city = tfcity.getText();
                String state = tfstate.getText();
                String email = tfemail.getText();
                String phone = tfphone.getText();

                String query1 = "insert into customer values('"+name+"', '"+meter+"', '"+address+"', '"+city+"', '"+state+"', '"+email+"', '"+phone+"')";
                String query2 = "insert into login values('"+meter+"', '', '"+name+"', '', '')";
                try {
                    Conn c = new Conn();
                    c.s.executeUpdate(query1);
                    c.s.executeUpdate(query2);
                    JOptionPane.showMessageDialog(null, "Customer Details Added Successfully");
                    setVisible(false);
                    new MeterInfo(meter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                setVisible(false);
            }
        }
    }

    // meterInfo Class (display meter information to the customer)
    static class MeterInfo extends JFrame {
        MeterInfo(String meter) {
            setTitle("Meter Info");
            setSize(400, 300);
            setLocation(400, 200);

            JLabel label = new JLabel("Meter Number: " + meter);
            label.setBounds(50, 50, 200, 30);
            add(label);

            setLayout(null);
            setVisible(true);
        }
    }

    // project class (Dashboard for Admin and Customer)
    static class Project extends JFrame {
        Project(String user, String meterNo) {
            setTitle("Welcome to " + user + " Dashboard");
            setSize(500, 400);
            setLocation(400, 200);

            if (user.equals("Admin")) {
                // Admin dashboard buttons can be added here
                JLabel label = new JLabel("Welcome Admin");
                label.setBounds(100, 50, 200, 30);
                add(label);
            } else {
                // Customer dashboard buttons
                JButton payBillButton = new JButton("Pay Bill");
                payBillButton.setBounds(100, 100, 200, 30);
                payBillButton.addActionListener(e -> new PayBill(meterNo));
                add(payBillButton);
            }

            setLayout(null);
            setVisible(true);
        }
    }

    // PayBill Class (for paying bills)
    static class PayBill extends JFrame implements ActionListener {
        JTextField tfUnits, tfAmount;
        JButton submit, cancel;

        PayBill(String meterNo) {
            setSize(500, 400);
            setLocation(400, 200);
            setTitle("Pay Bill");

            JLabel lblMeter = new JLabel("Meter Number: " + meterNo);
            lblMeter.setBounds(20, 30, 150, 20);
            add(lblMeter);

            JLabel lblUnits = new JLabel("Units Consumed: ");
            lblUnits.setBounds(20, 70, 150, 20);
            add(lblUnits);

            tfUnits = new JTextField();
            tfUnits.setBounds(180, 70, 150, 20);
            add(tfUnits);

            JLabel lblAmount = new JLabel("Amount: ");
            lblAmount.setBounds(20, 110, 150, 20);
            add(lblAmount);

            tfAmount = new JTextField();
            tfAmount.setBounds(180, 110, 150, 20);
            add(tfAmount);

            submit = new JButton("Submit");
            submit.setBounds(100, 150, 100, 30);
            submit.addActionListener(this);
            add(submit);

            cancel = new JButton("Cancel");
            cancel.setBounds(230, 150, 100, 30);
            cancel.addActionListener(this);
            add(cancel);

            setLayout(null);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == submit) {
                String units = tfUnits.getText();
                String amount = tfAmount.getText();
                try {
                    Conn c = new Conn();
                    String query = "insert into bill (meter_no, units, amount) values ('"+units+"', '"+amount+"')";
                    c.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Bill Paid Successfully!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                setVisible(false);
            }
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        new Login(); // This will initialize the login page
    }
}
