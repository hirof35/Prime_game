# PrimeGame - NEON EDITION

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Swing](https://img.shields.io/badge/GUI-Swing-blue?style=for-the-badge)

表示される数字が「素数」か「合成数」かを瞬時に判断する、スピード感あふれる脳トレゲームです。

## 🌟 特徴

*   **ネオン・エステティクス**: ダークモードを基調とした、視認性の高いネオンUI。
*   **ダイナミック演出**: 正解・不正解時に画面がフラッシュするフィードバック機能。
*   **難易度スケーリング**: スコアが上がるにつれて出題範囲が拡大し、より大きな数字が登場。
*   **ハイスコア保存**: ローカルファイルに最高記録を自動保存。
*   **タイムボーナス**: 正解するたびに残り時間が回復。大きな数字（100以上）を当てるとさらにボーナスがアップ。

## 🎮 遊び方

1.  画面中央に数字が表示されます。
2.  その数字が**素数**だと思ったら「素数」ボタンを、**合成数**（素数以外）だと思ったら「合成数」ボタンを押してください。
3.  制限時間がなくなるとゲームオーバーです。
4.  ミスをせずにハイスコアを目指しましょう！

## 🛠 動作環境

*   **Java 11以上** ( `Files.readString` を使用しているため)
*   **OS**: Windows, macOS, Linux (Swingが動作する環境)

## 🚀 セットアップと実行

1.  リポジトリをクローンします。
    ```bash
    git clone [https://github.com/YOUR_USERNAME/prime-game.git](https://github.com/YOUR_USERNAME/prime-game.git)
    ```
2.  ディレクトリに移動します。
    ```bash
    cd prime-game
    ```
3.  コンパイルします。
    ```bash
    javac primeGame/PrimeGame.java
    ```
4.  実行します。
    ```bash
    java primeGame.PrimeGame
    ```

## 📂 ファイル構造

*   `PrimeGame.java`: メインのソースコード（GUI、ロジック、タイマー処理）。
*   `high_score.txt`: ハイスコアが保存されるファイル（実行時に自動生成されます）。

## 📝 ライセンス

[MIT License](LICENSE)
<img width="542" height="485" alt="スクリーンショット 2026-05-03 104804" src="https://github.com/user-attachments/assets/d36fd01e-032c-4ae5-a1a6-6429c9f4dd0b" />
