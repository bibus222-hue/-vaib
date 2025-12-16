from flask import Flask, render_template, request, jsonify, redirect, url_for
import os

app = Flask(__name__, template_folder='../templates')

# Пример простой аутентификации
users = {
    "admin": "password123",
    "user": "userpass"
}

# Новые GET роутеры
@app.route('/')
def home():
    return render_template('home.html')

@app.route('/login', methods=['GET'])
def login_page():
    return render_template('login.html')  # если есть

@app.route('/data-form')
def data_form():
    return render_template('form.html')

@app.route('/result')
def result():
    return render_template('result.html', message="Данные получены!")

# POST маршруты для проверки
@app.route('/submit-form', methods=['POST'])
def submit_form():
    data = request.json
    # Пример обработки
    if data.get('username') and data.get('email'):
        return jsonify({"status": "success", "message": "Данные сохранены"}), 200
    return jsonify({"status": "error", "message": "Неверные данные"}), 400

@app.route('/auth', methods=['POST'])
def authenticate():
    data = request.json
    username = data.get('username')
    password = data.get('password')
    if users.get(username) == password:
        return jsonify({"status": "success", "message": "Аутентификация успешна"}), 200
    return jsonify({"status": "error", "message": "Неверный логин или пароль"}), 401

if __name__ == '__main__':
    app.run(debug=True, port=5000)