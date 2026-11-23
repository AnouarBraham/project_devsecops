package com.cybersecure.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
@RestController
public class CybersecurePlatformApplication {
    

    private static final Set<String> COMMON_PASSWORDS = Set.of(
        "password", "123456", "12345678", "qwerty", "abc123",
        "letmein", "admin", "welcome", "monkey", "dragon",
        "123456789", "iloveyou", "sunshine", "princess", "football"
    );


    public static void main(String[] args) {
        SpringApplication.run(CybersecurePlatformApplication.class, args);
    }

    // ---------- Page ----------

    @GetMapping("/")
    public String home() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>CyberSecure Platform</title>
                <style>
                    * { margin: 0; padding: 0; box-sizing: border-box; }
                    body {
                        font-family: 'Segoe UI', Arial, sans-serif;
                        background: #0d1117;
                        color: #c9d1d9;
                        min-height: 100vh;
                    }
                    nav {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        padding: 18px 40px;
                        background: #161b22;
                        border-bottom: 1px solid #30363d;
                    }
                    nav .logo { font-size: 1.3rem; font-weight: bold; color: #58a6ff; }
                    nav ul { list-style: none; display: flex; gap: 26px; }
                    nav a { color: #8b949e; text-decoration: none; font-size: 0.95rem; }
                    nav a:hover { color: #58a6ff; }

                    .hero { text-align: center; padding: 60px 20px 30px; }
                    .hero h1 { color: #58a6ff; font-size: 2.6rem; margin-bottom: 14px; }
                    .hero p { color: #8b949e; font-size: 1.1rem; }

                    .container { max-width: 900px; margin: 0 auto; padding: 20px 20px 60px; }

                    .checker {
                        background: #161b22;
                        border: 1px solid #30363d;
                        border-radius: 12px;
                        padding: 28px;
                        margin: 30px 0 50px;
                    }
                    .checker h2 { color: #58a6ff; margin-bottom: 18px; }
                    .checker p.hint { color: #8b949e; font-size: 0.9rem; margin-bottom: 14px; }

                    .row { display: flex; gap: 12px; }
                    .row input {
                        flex: 1;
                        padding: 12px 14px;
                        background: #0d1117;
                        border: 1px solid #30363d;
                        border-radius: 8px;
                        color: #c9d1d9;
                        font-size: 1rem;
                        font-family: inherit;
                    }
                    .row input:focus { outline: none; border-color: #58a6ff; }
                    .row button {
                        background: #238636;
                        color: white;
                        border: none;
                        padding: 12px 22px;
                        border-radius: 8px;
                        font-size: 1rem;
                        cursor: pointer;
                        transition: background 0.2s;
                    }
                    .row button:hover { background: #2ea043; }

                    .result { margin-top: 22px; display: none; }
                    .result.visible { display: block; }
                    .bar {
                        height: 10px;
                        background: #30363d;
                        border-radius: 5px;
                        overflow: hidden;
                        margin-bottom: 10px;
                    }
                    .bar-fill {
                        height: 100%;
                        width: 0%;
                        transition: width 0.3s, background 0.3s;
                    }
                    .level-text { font-weight: bold; margin-bottom: 6px; }
                    .score-text { color: #8b949e; font-size: 0.9rem; margin-bottom: 14px; }
                    .feedback { list-style: none; }
                    .feedback li {
                        padding: 6px 0;
                        color: #c9d1d9;
                        font-size: 0.92rem;
                    }
                    .feedback li::before { content: "• "; color: #58a6ff; }

                    .cards {
                        display: grid;
                        grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
                        gap: 22px;
                        margin-bottom: 50px;
                    }
                    .card {
                        background: #161b22;
                        border: 1px solid #30363d;
                        border-radius: 10px;
                        padding: 24px;
                        transition: border-color 0.2s;
                    }
                    .card:hover { border-color: #58a6ff; }
                    .card h3 { color: #58a6ff; margin-bottom: 10px; font-size: 1.1rem; }
                    .card p { color: #8b949e; line-height: 1.6; font-size: 0.95rem; }

                    .threats {
                        background: #161b22;
                        border: 1px solid #30363d;
                        border-radius: 12px;
                        padding: 28px;
                    }
                    .threats h2 { color: #58a6ff; margin-bottom: 18px; }
                    table { width: 100%; border-collapse: collapse; }
                    th, td {
                        text-align: left;
                        padding: 12px 10px;
                        border-bottom: 1px solid #30363d;
                        font-size: 0.92rem;
                    }
                    th { color: #8b949e; font-weight: normal; }
                    td { color: #c9d1d9; }
                    .sev-high { color: #f85149; font-weight: bold; }
                    .sev-med { color: #d29922; font-weight: bold; }

                    footer {
                        text-align: center;
                        padding: 30px;
                        color: #8b949e;
                        font-size: 0.9rem;
                        border-top: 1px solid #30363d;
                    }
                </style>
            </head>
            <body>

                <nav>
                    <div class="logo">🛡️ CyberSecure</div>
                    <ul>
                        <li><a href="#checker">Password Checker</a></li>
                        <li><a href="#tools">Tips</a></li>
                        <li><a href="#threats">Threats</a></li>
                    </ul>
                </nav>

                <section class="hero">
                    <h1>CyberSecure Platform</h1>
                    <p>Learn cybersecurity fundamentals with real, server-side validation.</p>
                </section>

                <div class="container">

                    <div class="checker" id="checker">
                        <h2>Password Strength Checker</h2>
                        <p class="hint">Your password is sent to the backend and evaluated with real Java logic. It is never stored.</p>
                        <div class="row">
                            <input type="password" id="pwd" placeholder="Enter a password to test..." />
                            <button onclick="checkPassword()">Check</button>
                        </div>

                        <div class="result" id="result">
                            <div class="bar"><div class="bar-fill" id="barFill"></div></div>
                            <div class="level-text" id="levelText"></div>
                            <div class="score-text" id="scoreText"></div>
                            <ul class="feedback" id="feedback"></ul>
                        </div>
                    </div>

                    <div class="cards" id="tools">
                        <div class="card">
                            <h3>🔐 Password Security</h3>
                            <p>Create strong passwords and protect your accounts with unique credentials.</p>
                        </div>
                        <div class="card">
                            <h3>🎣 Phishing Awareness</h3>
                            <p>Recognize suspicious emails, links and social engineering attacks.</p>
                        </div>
                        <div class="card">
                            <h3>🔑 Multi-Factor Authentication</h3>
                            <p>Add an extra layer of protection to your online accounts.</p>
                        </div>
                        <div class="card">
                            <h3>🌐 Network Security</h3>
                            <p>Discover network scanning and traffic analysis techniques.</p>
                        </div>
                        <div class="card">
                            <h3>🧰 Security Tools</h3>
                            <p>Explore tools such as Nmap, Wireshark and Burp Suite.</p>
                        </div>
                        <div class="card">
                            <h3>⚠️ Vulnerability Awareness</h3>
                            <p>Understand common vulnerabilities and why security updates matter.</p>
                        </div>
                    </div>

                    <div class="threats" id="threats">
                        <h2>Latest Threats</h2>
                        <table>
                            <thead>
                                <tr><th>CVE</th><th>Description</th><th>Severity</th></tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>CVE-2024-3094</td>
                                    <td>Backdoor in xz-utils affecting SSH</td>
                                    <td class="sev-high">High</td>
                                </tr>
                                <tr>
                                    <td>CVE-2023-44487</td>
                                    <td>HTTP/2 Rapid Reset denial of service</td>
                                    <td class="sev-high">High</td>
                                </tr>
                                <tr>
                                    <td>CVE-2024-6387</td>
                                    <td>OpenSSH regreSSHion remote code execution</td>
                                    <td class="sev-med">Medium</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                </div>

                <footer>
                    CyberSecure Platform &mdash; DevSecOps Project
                </footer>

                <script>
                    async function checkPassword() {
                        const pwd = document.getElementById('pwd').value;
                        const result = document.getElementById('result');

                        const response = await fetch('/api/password-check', {
                            method: 'POST',
                            headers: { 'Content-Type': 'application/json' },
                            body: JSON.stringify({ password: pwd })
                        });

                        const data = await response.json();
                        renderResult(data);
                        result.classList.add('visible');
                    }

                    function renderResult(data) {
                        const fill = document.getElementById('barFill');
                        const levelText = document.getElementById('levelText');
                        const scoreText = document.getElementById('scoreText');
                        const feedbackList = document.getElementById('feedback');

                        const colors = ['#f85149', '#f85149', '#d29922', '#d29922', '#3fb950'];
                        fill.style.width = (data.score * 20) + '%';
                        fill.style.background = colors[data.score - 1] || '#30363d';

                        levelText.textContent = data.level;
                        levelText.style.color = colors[data.score - 1] || '#c9d1d9';
                        scoreText.textContent = 'Score: ' + data.score + ' / 5';

                        feedbackList.innerHTML = '';
                        data.feedback.forEach(msg => {
                            const li = document.createElement('li');
                            li.textContent = msg;
                            feedbackList.appendChild(li);
                        });
                    }
                </script>

            </body>
            </html>
            """;
    }

    // ---------- API ----------

    @PostMapping("/api/password-check")
    public Map<String, Object> checkPassword(@RequestBody Map<String, String> body) {
        String password = body.getOrDefault("password", "");
        return evaluatePassword(password);
    }

    /**
     * Real password strength evaluation.
     * Score 1..5 based on:
     *   - presence of common/known password
     *   - length
     *   - character variety (lower, upper, digit, special)
     * Returns level + per-rule feedback.
     */
    private Map<String, Object> evaluatePassword(String password) {
        List<String> feedback = new ArrayList<>();

        // Reject common passwords outright
        if (COMMON_PASSWORDS.contains(password.toLowerCase())) {
            feedback.add("This is a very common password. Do not use it.");
            return Map.of(
                "score", 1,
                "level", "Very Weak",
                "feedback", feedback
            );
        }

        int score = 0;

        // Length
        if (password.length() >= 8) {
            score++;
        } else {
            feedback.add("Use at least 8 characters.");
        }
        if (password.length() >= 12) {
            score++;
        } else if (password.length() >= 8) {
            feedback.add("12+ characters would be much stronger.");
        }

        // Character variety
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(c -> !Character.isLetterOrDigit(c));

        if (hasLower && hasUpper) {
            score++;
        } else {
            feedback.add("Mix uppercase and lowercase letters.");
        }
        if (hasDigit) {
            score++;
        } else {
            feedback.add("Add at least one digit.");
        }
        if (hasSpecial) {
            score++;
        } else {
            feedback.add("Add a special character (e.g. ! @ # $ %).");
        }

        // Cap score
        if (score > 5) score = 5;
        if (score < 1) score = 1;

        // Level mapping
        String level;
        if (score <= 1) level = "Very Weak";
        else if (score == 2) level = "Weak";
        else if (score == 3) level = "Medium";
        else if (score == 4) level = "Strong";
        else level = "Very Strong";

        if (feedback.isEmpty()) {
            feedback.add("Excellent. This password meets all our checks.");
        }

        return Map.of(
            "score", score,
            "level", level,
            "feedback", feedback
        );
    }
}