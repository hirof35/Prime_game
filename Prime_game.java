package primeGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class PrimeGame extends JFrame {
    private int score = 0;
    private int highScore = 0;
    private int timeLeft = 10;
    private int currentNumber;
    private int maxRange = 50;
    
    private JLabel numberLabel, scoreLabel, timeLabel, highScoreLabel;
    private JPanel mainPanel; // 背景色を変えるためのパネル
    private Timer gameTimer;
    private final String SCORE_FILE = "high_score.txt";

    // 色の定義（ネオン風）
    private final Color COLOR_BG = new Color(30, 30, 30);
    private final Color COLOR_TEXT = Color.WHITE;
    private final Color COLOR_CORRECT = new Color(50, 205, 50); // 鮮やかな緑
    private final Color COLOR_WRONG = new Color(220, 20, 60);    // 鮮やかな赤

    public PrimeGame() {
        loadHighScore();
        
        setTitle("素数判定：NEON EDITION");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // メインパネルの設定（フラッシュ演出用）
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_BG);
        setContentPane(mainPanel);

        // 上部情報エリア
        JPanel infoPanel = new JPanel(new GridLayout(2, 2));
        infoPanel.setOpaque(false); // 背景を透過
        scoreLabel = createStyledLabel("スコア: 0", 18);
        highScoreLabel = createStyledLabel("ハイスコア: " + highScore, 18);
        timeLabel = createStyledLabel("残り時間: 10", 24);
        timeLabel.setForeground(Color.YELLOW);
        
        infoPanel.add(scoreLabel);
        infoPanel.add(highScoreLabel);
        infoPanel.add(new JLabel("")); // スペーサー
        infoPanel.add(timeLabel);
        mainPanel.add(infoPanel, BorderLayout.NORTH);

        // 数字表示エリア（フォントを太く、大きく）
        numberLabel = new JLabel("", JLabel.CENTER);
        numberLabel.setFont(new Font("Monospaced", Font.BOLD, 80));
        numberLabel.setForeground(COLOR_TEXT);
        mainPanel.add(numberLabel, BorderLayout.CENTER);

        // ボタンエリア
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton primeButton = createStyledButton("素数", new Color(0, 191, 255));
        JButton notPrimeButton = createStyledButton("合成数", new Color(255, 165, 0));
        
        buttonPanel.add(primeButton);
        buttonPanel.add(notPrimeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        primeButton.addActionListener(e -> checkAnswer(true));
        notPrimeButton.addActionListener(e -> checkAnswer(false));

        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            timeLabel.setText("残り時間: " + timeLeft);
            if (timeLeft <= 3) timeLabel.setForeground(COLOR_WRONG); // 残り3秒で赤く
            if (timeLeft <= 0) gameOver();
        });

        startNewGame();
    }

    // スタイル付きラベル作成の補助
    private JLabel createStyledLabel(String text, int size) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setForeground(COLOR_TEXT);
        label.setFont(new Font("SansSerif", Font.BOLD, size));
        return label;
    }

    // スタイル付きボタン作成の補助
    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 20));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(160, 60));
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        return btn;
    }

    private void startNewGame() {
        score = 0;
        maxRange = 50;
        timeLeft = 10;
        timeLabel.setForeground(Color.YELLOW);
        updateLabels();
        nextQuestion();
        gameTimer.start();
    }

    private void nextQuestion() {
        currentNumber = new Random().nextInt(maxRange - 1) + 2;
        numberLabel.setText(String.valueOf(currentNumber));
        numberLabel.setForeground(COLOR_TEXT);
        // 数字の大きさを桁数に合わせて調整
        if (currentNumber >= 100) numberLabel.setFont(new Font("Monospaced", Font.BOLD, 60));
        else numberLabel.setFont(new Font("Monospaced", Font.BOLD, 90));
    }

    private void checkAnswer(boolean userSaidPrime) {
        if (isPrime(currentNumber) == userSaidPrime) {
            score++;
            flashBackground(COLOR_CORRECT); // 正解時の緑フラッシュ
            
            maxRange += 20;
            if (currentNumber >= 100) timeLeft = Math.min(20, timeLeft + 5);
            else timeLeft = Math.min(15, timeLeft + 2);
            
            updateLabels();
            nextQuestion();
        } else {
            flashBackground(COLOR_WRONG); // 不正解時の赤フラッシュ
            gameOver();
        }
    }

    // ★ 画面フラッシュ演出 ★
    private void flashBackground(Color color) {
        mainPanel.setBackground(color);
        // 100ミリ秒後に元の色に戻すタイマー
        Timer flashTimer = new Timer(100, e -> mainPanel.setBackground(COLOR_BG));
        flashTimer.setRepeats(false);
        flashTimer.start();
    }

    private void updateLabels() {
        scoreLabel.setText("スコア: " + score);
        timeLabel.setText("残り時間: " + timeLeft);
    }

    private void gameOver() {
        gameTimer.stop();
        numberLabel.setText("FINISH!");
        numberLabel.setForeground(COLOR_WRONG);
        
        String message = "スコア: " + score;
        if (score > highScore) {
            highScore = score;
            saveHighScore();
            message += "\nNEW RECORD!!";
            highScoreLabel.setText("ハイスコア: " + highScore);
        }
        
        JOptionPane.showMessageDialog(this, message);
        startNewGame();
    }

    // --- (以下、loadHighScore, saveHighScore, isPrime は前回と同じ) ---
    private void loadHighScore() {
        try { if (Files.exists(Paths.get(SCORE_FILE))) highScore = Integer.parseInt(Files.readString(Paths.get(SCORE_FILE)).trim());
        } catch (Exception e) { highScore = 0; }
    }
    private void saveHighScore() {
        try { Files.writeString(Paths.get(SCORE_FILE), String.valueOf(highScore)); } catch (IOException e) { e.printStackTrace(); }
    }
    private boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(n); i += 2) if (n % i == 0) return false;
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PrimeGame().setVisible(true));
    }
}
