/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatClient;

/**
 *
 * @author spiderman
 */

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Date;

public class ChatClient extends JFrame {

    private static final int PORT = 5123;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    private boolean connected = false;

    private JTextPane chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JButton connectButton;
    private JTextField serverField;
    private JTextField usernameField;
    private JList<String> userList;
    private DefaultListModel<String> userListModel;
    private JLabel statusLabel;
    private StyledDocument doc;

    private static final Color COLOR_BG_MAIN       = new Color(43,  43,  43);
    private static final Color COLOR_BG_HEADER     = new Color(60,  63,  65);
    private static final Color COLOR_BG_SIDEBAR    = new Color(49,  51,  53);
    private static final Color COLOR_ACCENT_BLUE   = new Color(77,  152, 255);
    private static final Color COLOR_ACCENT_GREEN  = new Color(98,  150, 85);
    private static final Color COLOR_ACCENT_YELLOW = new Color(204, 120, 50);
    private static final Color COLOR_ACCENT_RED    = new Color(255, 100, 100);
    private static final Color COLOR_ACCENT_PURPLE = new Color(152, 118, 170);
    private static final Color COLOR_TEXT_PRIMARY  = new Color(187, 187, 187);
    private static final Color COLOR_TEXT_SECONDARY= new Color(128, 128, 128);

    private static final Font FONT_TEXT      = new Font("Monospaced", Font.PLAIN, 13);
    private static final Font FONT_UI        = new Font("Segoe UI",   Font.PLAIN, 13);
    private static final Font FONT_UI_BOLD   = new Font("Segoe UI",   Font.BOLD,  13);
    private static final Font FONT_UI_HEADER = new Font("Segoe UI",   Font.BOLD,  15);

    private Style styleNormal, styleSystem, styleSelf, styleOther, styleTime;

    public ChatClient() {
        super("Java Chat Terminal");
        initializeUI();
        configureWindow();
    }

    private void configureWindow() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (connected) disconnect();
                System.exit(0);
            }
        });
        setSize(950, 650);
        setMinimumSize(new Dimension(750, 500));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeUI() {
        getContentPane().setBackground(COLOR_BG_MAIN);
        setLayout(new BorderLayout());
        add(createTopPanel(),    BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
        add(createInputArea(),   BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        panel.setBackground(COLOR_BG_HEADER);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BG_MAIN));

        JLabel brand = new JLabel("CHAT SYSTEM");
        brand.setForeground(COLOR_ACCENT_PURPLE);
        brand.setFont(FONT_UI_HEADER);
        panel.add(brand);
        panel.add(Box.createRigidArea(new Dimension(10, 1)));

        serverField = createStyledTextField("localhost", 10);
        panel.add(createStaticLabel("Server:"));
        panel.add(serverField);

        usernameField = createStyledTextField("User" + (int)(Math.random() * 899 + 100), 8);
        panel.add(createStaticLabel("Username:"));
        panel.add(usernameField);

        connectButton = new JButton("Connect");
        applyButtonStyle(connectButton, COLOR_ACCENT_GREEN);
        connectButton.addActionListener(e -> toggleConnection());
        panel.add(connectButton);

        statusLabel = new JLabel("○ Offline");
        statusLabel.setForeground(COLOR_ACCENT_RED);
        statusLabel.setFont(FONT_UI);
        panel.add(statusLabel);

        return panel;
    }

    private JSplitPane createMainContent() {
        chatArea = new JTextPane();
        chatArea.setEditable(false);
        chatArea.setBackground(COLOR_BG_MAIN);
        chatArea.setForeground(COLOR_TEXT_PRIMARY);
        chatArea.setFont(FONT_TEXT);
        chatArea.setMargin(new Insets(10, 12, 10, 12));
        doc = chatArea.getStyledDocument();
        setupTextStyles();

        JScrollPane chatScroll = new JScrollPane(chatArea);
        chatScroll.setBorder(null);
        chatScroll.getViewport().setBackground(COLOR_BG_MAIN);
        chatScroll.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override protected void configureScrollBarColors() {
                this.thumbColor = new Color(100, 100, 100);
                this.trackColor = COLOR_BG_MAIN;
            }
        });

        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setBackground(COLOR_BG_SIDEBAR);
        userList.setForeground(COLOR_TEXT_PRIMARY);
        userList.setFont(FONT_UI);
        userList.setFixedCellHeight(30);
        userList.setCellRenderer(new UserListRenderer());

        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(COLOR_BG_SIDEBAR);
        JLabel title = new JLabel("   Online Users");
        title.setPreferredSize(new Dimension(150, 40));
        title.setForeground(COLOR_ACCENT_BLUE);
        title.setFont(FONT_UI_BOLD);
        sidebar.add(title, BorderLayout.NORTH);

        JScrollPane userScroll = new JScrollPane(userList);
        userScroll.setBorder(null);
        sidebar.add(userScroll, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chatScroll, sidebar);
        splitPane.setDividerLocation(700);
        splitPane.setDividerSize(2);
        splitPane.setBorder(null);
        return splitPane;
    }

    private JPanel createInputArea() {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(COLOR_BG_HEADER);
        panel.setBorder(new EmptyBorder(10, 15, 10, 15));

        messageField = new JTextField();
        messageField.setBackground(COLOR_BG_MAIN);
        messageField.setForeground(COLOR_TEXT_PRIMARY);
        messageField.setCaretColor(COLOR_ACCENT_BLUE);
        messageField.setFont(FONT_UI);
        messageField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BG_SIDEBAR),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        messageField.setEnabled(false);
        messageField.addActionListener(e -> sendMessage());

        sendButton = new JButton("Send Message");
        applyButtonStyle(sendButton, COLOR_ACCENT_BLUE);
        sendButton.setEnabled(false);
        sendButton.addActionListener(e -> sendMessage());

        panel.add(messageField, BorderLayout.CENTER);
        panel.add(sendButton,   BorderLayout.EAST);
        return panel;
    }

    private void setupTextStyles() {
        styleNormal = createStyle("normal", COLOR_TEXT_PRIMARY,   false);
        styleSystem = createStyle("system", COLOR_ACCENT_YELLOW,  true);
        styleSelf   = createStyle("self",   COLOR_ACCENT_GREEN,   false);
        styleOther  = createStyle("other",  COLOR_ACCENT_BLUE,    false);
        styleTime   = createStyle("time",   COLOR_TEXT_SECONDARY, false);
    }

    private Style createStyle(String name, Color color, boolean bold) {
        Style s = chatArea.addStyle(name, null);
        StyleConstants.setForeground(s, color);
        StyleConstants.setBold(s, bold);
        return s;
    }

    private void toggleConnection() {
        if (!connected) connect();
        else disconnect();
    }

    private void connect() {
        String host = serverField.getText().trim();
        username = usernameField.getText().trim();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "A username is required.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        connectButton.setEnabled(false);
        connectButton.setText("Connecting...");

        new Thread(() -> {
            try {
                socket = new Socket(host.isEmpty() ? "localhost" : host, PORT);
                out = new PrintWriter(socket.getOutputStream(), true);
                in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println(username); // server expects username as the first line
                connected = true;

                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("● Online: " + username);
                    statusLabel.setForeground(COLOR_ACCENT_GREEN);
                    connectButton.setText("Disconnect");
                    connectButton.setEnabled(true);
                    applyButtonStyle(connectButton, COLOR_ACCENT_RED);
                    messageField.setEnabled(true);
                    sendButton.setEnabled(true);
                    serverField.setEnabled(false);
                    usernameField.setEnabled(false);
                    messageField.requestFocusInWindow();
                });

                startListenerThread();

            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> {
                    appendLine("System: Failed to connect - " + e.getMessage(), styleSystem);
                    resetUIState();
                });
            }
        }).start();
    }

    private void startListenerThread() {
        new Thread(() -> {
            try {
                String line;
                while (connected && (line = in.readLine()) != null) {
                    final String msg = line;
                    SwingUtilities.invokeLater(() -> processIncomingMessage(msg));
                }
            } catch (IOException e) {
                if (connected) {
                    SwingUtilities.invokeLater(() -> {
                        appendLine("System: Connection to server was lost.", styleSystem);
                        resetUIState();
                    });
                }
            }
        }, "ChatListener").start();
    }

    private void processIncomingMessage(String msg) {
        if (msg.startsWith("/welcome ")) {
            appendLine(msg.substring(9), styleSystem);

        } else if (msg.startsWith("/userlist ")) {
            updateUserList(msg.substring(10));

        } else if (msg.contains("You: ")) {
            // server echoes own messages as "[HH:mm:ss] You: text" so startsWith won't work
            appendLine(msg, styleSelf);

        } else if (msg.contains("★") || msg.contains("✖")) {
            // join (★) and leave (✖) notifications
            appendLine(msg, styleSystem);

        } else {
            appendLine(msg, styleOther);
        }
        scrollChatToBottom();
    }

    private void sendMessage() {
        String text = messageField.getText().trim();
        if (!text.isEmpty() && connected) {
            out.println(text);
            messageField.setText("");
        }
    }

    private void disconnect() {
        connected = false;
        try {
            if (out != null) { out.println("/quit"); out.close(); }
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException ignored) {}

        resetUIState();
        appendLine("System: You have disconnected.", styleSystem);
    }

    private void resetUIState() {
        connected = false;
        statusLabel.setText("○ Offline");
        statusLabel.setForeground(COLOR_ACCENT_RED);
        connectButton.setText("Connect");
        connectButton.setEnabled(true);
        applyButtonStyle(connectButton, COLOR_ACCENT_GREEN);
        messageField.setEnabled(false);
        sendButton.setEnabled(false);
        serverField.setEnabled(true);
        usernameField.setEnabled(true);
        userListModel.clear();
    }

    private void updateUserList(String csv) {
        userListModel.clear();
        if (csv != null && !csv.isBlank()) {
            for (String user : csv.split(",")) {
                userListModel.addElement(user.trim());
            }
        }
    }

    private void appendLine(String text, Style style) {
        try {
            doc.insertString(doc.getLength(), text + "\n", style);
        } catch (BadLocationException ignored) {}
    }

    private void scrollChatToBottom() {
        chatArea.setCaretPosition(doc.getLength());
    }

    private void applyButtonStyle(JButton btn, Color color) {
        btn.setForeground(color);
        btn.setBackground(COLOR_BG_SIDEBAR);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(color, 1));
        btn.setOpaque(true);
        btn.setFont(FONT_UI_BOLD);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private JLabel createStaticLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(COLOR_TEXT_SECONDARY);
        l.setFont(FONT_UI);
        return l;
    }

    private JTextField createStyledTextField(String text, int cols) {
        JTextField f = new JTextField(text, cols);
        f.setBackground(COLOR_BG_MAIN);
        f.setForeground(COLOR_TEXT_PRIMARY);
        f.setCaretColor(COLOR_ACCENT_BLUE);
        f.setFont(FONT_UI);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BG_SIDEBAR),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        return f;
    }

    private class UserListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            String name = value.toString();
            label.setText("  ● " + name + (name.equals(username) ? " (me)" : ""));
            label.setForeground(name.equals(username) ? COLOR_ACCENT_GREEN : COLOR_TEXT_PRIMARY);
            label.setBackground(isSelected ? COLOR_BG_HEADER : COLOR_BG_SIDEBAR);
            label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            return label;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(ChatClient::new);
    }
}