from flask import *
from flask_sqlalchemy import SQLAlchemy
from flask_login import LoginManager, UserMixin, login_user, logout_user, current_user, login_required

app = Flask(__name__, static_url_path= '/static')
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///database.db'
app.config['SECRET_KEY'] = 'SuperSecretKeyString'
db = SQLAlchemy(app)

@app.route('/')
def index ():
    isLogedIn = current_user.is_authenticated
    return render_template('index.html', isLogedIn=isLogedIn)

@app.route('/create', methods=['POST', 'GET'])
def create ():
    if request.method == 'POST':
        name = request.form['name']
        age = request.form['age']
        password = request.form['password']
        username = request.form['username']
        user = User.query.filter_by(username = username).first()
        if user is None:
            user = User(name = name , age = age , password = password, username = username) 
            db.session.add(user)
            db.session.commit()
        else: render_template ('create.html', user=user)
    return render_template ('create.html')

@app.route('/delete/<id>')
@login_required
def delete (id):
    runs = Runs.query.filter_by(id=id).first()
    db.session.delete(runs)
    db.session.commit()
    return redirect(url_for('view', Runs=runs))
    
@app.route('/runs', methods=['POST', 'GET'])
@login_required
def runs ():
    if request.method == 'POST':
        runName = request.form['runName']
        time = request.form['time']
        difficulty = request.form['difficulty']
        runs = Runs(runName = runName , time = time , difficulty = difficulty, user_id = current_user.id)
        db.session.add(runs)
        db.session.commit()
    return render_template ('runs.html')

@app.route('/update/<id>', methods=['POST', 'GET'])
@login_required
def update (id):
    if request.method =='GET':
        runs = Runs.query.filter_by(id=id).first()
        return render_template ('update.html', Runs=runs)
    if request.method == 'POST':
        runs = Runs.query.filter_by(id=id).first()
        runs.runName = request.form['runName']
        runs.time = request.form['time']
        runs.difficulty = request.form['difficulty']
        db.session.commit()
        return redirect(url_for('view', Runs=runs))

class User(UserMixin, db.Model):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(20), unique=True, nullable=False)
    password = db.Column(db.String(40), nullable=False)
    age = db.Column(db.Integer)
    name = db.Column(db.String(40), nullable=False)

class Runs(UserMixin, db.Model):
    id = db.Column(db.Integer, primary_key=True)
    runName = db.Column(db.String(80))
    time = db.Column(db.Float)
    difficulty = db.Column(db.String(80))
    user_id = db.Column(db.Integer, db.ForeignKey(User.id))

login_manager = LoginManager(app)
login_manager.init_app(app)

@login_manager.user_loader
def load_user(uid):
    return User.query.get(uid)

@app.route('/login', methods = ['POST', 'GET'])
def login():
    if request.method == 'GET':
        return ''' <form method=POST>
                     username: <input type="text" name="username"><br>
                     password: <input type="password" name="password"<br>
                     <input type="submit">'''

    username = request.form['username']
    password = request.form['password']
    user = User.query.filter_by(username=username, password=password).first()
    if user is None or user.password != password:
        return "Username or Password is incorrect"
        return redirect(url_for('login'))
    
    if user.password == password:
        login_user(user)
        return redirect(url_for('index'))

@app.route('/view')
@login_required
def view ():
    runs = Runs.query.filter_by(user_id=current_user.id).all()
    return render_template ('view.html', runs=runs)

@app.route('/logout')
@login_required
def logout():
    logout_user()
    return redirect(url_for('index'))
@app.route('/secret')
@login_required
def secret():
    return render_template('funnymemes.html')

if __name__ == '__main__':
    app.run()