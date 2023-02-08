package main

import (
	"database/sql"
	"fmt"
	"html/template"
	"log"
	"net/http"
	"strconv"

	"github.com/gorilla/sessions"

	_ "github.com/mattn/go-sqlite3"
)

type Animals struct {
	Id   int
	Name string
	Age  int
	Uid  int
}
type User struct {
	Uid      int
	Name     string
	Password string
}

var globalUser int = -1
var (
	key   = []byte("super-secret-key")
	store = sessions.NewCookieStore(key)
	db    *sql.DB
)

func exitIfNil(err error) {
	if err != nil {
		log.Fatal(err)
	}
}
func home(writer http.ResponseWriter, request *http.Request) {
	html, err := template.ParseFiles("home.html")
	fmt.Println("its working")
	html.Execute(writer, nil)

	exitIfNil(err)

}
func create(writer http.ResponseWriter, request *http.Request) {
	session, _ := store.Get(request, "cookie-name")
	auth, ok := session.Values["authenticated"].(bool)
	if !ok || !auth || globalUser == -1 {
		http.Redirect(writer, request, "/login", http.StatusFound)
	} else {
		html, err := template.ParseFiles("create.html")
		fmt.Println("create is working")
		method := request.Method
		fmt.Println(method)
		if method == "GET" {
			html.Execute(writer, nil)
			exitIfNil(err)
			name := request.FormValue("name")
			age := request.FormValue("age")

			fmt.Println(name)
			fmt.Println(age)

		}
		if method == "POST" {
			name := request.FormValue("name")
			age := request.FormValue("age")
			fmt.Println(name)
			fmt.Println(age)
			db, err := sql.Open("sqlite3", "./animals.sqlite")
			exitIfNil(err)
			defer db.Close()

			stmt := "INSERT INTO animals (name, age, Uid) VALUES ($1, $2, $3)"

			_, err = db.Exec(stmt, name, age, globalUser)
			exitIfNil(err)
			http.Redirect(writer, request, "/read", http.StatusFound)
		}
	}

}
func read(writer http.ResponseWriter, request *http.Request) {
	session, _ := store.Get(request, "cookie-name")
	auth, ok := session.Values["authenticated"].(bool)
	if !ok || !auth || globalUser == -1 {
		http.Redirect(writer, request, "/login", http.StatusFound)
	} else {
		session, _ := store.Get(request, "cookie-name")
		auth, ok := session.Values["authenticated"].(bool)
		if !ok || !auth {
			http.Redirect(writer, request, "/login", http.StatusFound)
		} else {

			html, err := template.ParseFiles("read.html")
			fmt.Println("read is working")
			db, err := sql.Open("sqlite3", "./animals.sqlite")
			exitIfNil(err)
			defer db.Close()

			cmd := "SELECT * FROM animals WHERE Uid = $1"
			rows, err := db.Query(cmd, globalUser)
			var items []Animals
			for rows.Next() {
				var item Animals

				err := rows.Scan(&item.Id, &item.Name, &item.Age, &item.Uid)
				exitIfNil(err)
				items = append(items, item)
			}
			html.Execute(writer, items)
		}
	}
}
func user(writer http.ResponseWriter, request *http.Request) {
	session, _ := store.Get(request, "cookie-name")
	auth, ok := session.Values["authenticated"].(bool)
	if !ok || !auth || globalUser == -1 {
		html, err := template.ParseFiles("user.html")
		fmt.Println("user is working")
		method := request.Method
		fmt.Println(method)
		if method == "GET" {
			html.Execute(writer, nil)
			exitIfNil(err)
			name := request.FormValue("name")
			password := request.FormValue("password")
			http.Redirect(writer, request, "/", http.StatusFound)
			fmt.Println(name)
			fmt.Println(password)

		}
		if method == "POST" {
			name := request.FormValue("name")
			password := request.FormValue("password")
			fmt.Println(name)
			fmt.Println(password)
			db, err := sql.Open("sqlite3", "./animals.sqlite")
			exitIfNil(err)
			defer db.Close()

			stmt := "INSERT INTO user" +
				"(Username,Password)" +
				"VALUES" +
				"('" + name + "'," + password + ")"
			_, err = db.Exec(stmt)
			exitIfNil(err)
			http.Redirect(writer, request, "/", http.StatusFound)
		}
	} else {

		http.Redirect(writer, request, "/", http.StatusFound)
	}

}
func userCheck(x string, y string) int {
	fmt.Println(x)
	fmt.Println(y)
	db, err := sql.Open("sqlite3", "./animals.sqlite")
	exitIfNil(err)
	defer db.Close()
	cmd := "SELECT Uid, Password FROM user WHERE Username = $1"
	rows, err := db.Query(cmd, x)
	defer rows.Close()
	exitIfNil(err)
	for rows.Next() {
		var id int
		var password string
		err := rows.Scan(&id, &password)
		fmt.Println(id)
		fmt.Println(password)
		exitIfNil(err)
		if password == y {
			return id
		}
	}
	return -1
}
func login(writer http.ResponseWriter, request *http.Request) {
	fmt.Println(globalUser)
	session, _ := store.Get(request, "cookie-name")
	auth, ok := session.Values["authenticated"].(bool)
	if !ok || !auth || globalUser == -1 {
		if request.Method == http.MethodGet {

			html, err := template.ParseFiles("login.html")
			fmt.Println("login is working")
			db, err := sql.Open("sqlite3", "./animals.sqlite")
			exitIfNil(err)
			defer db.Close()
			html.Execute(writer, nil)
		}
		if request.Method == http.MethodPost {
			err := request.ParseForm()
			exitIfNil(err)
			uname := request.PostFormValue("name")
			pword := request.PostFormValue("password")
			Uid := userCheck(uname, pword)
			globalUser = Uid
			if Uid != -1 {
				session.Values["authenticated"] = true
				session.Save(request, writer)
			}
			fmt.Println(globalUser)
			http.Redirect(writer, request, "http://localhost:5000", http.StatusFound)
		} else {

			http.Redirect(writer, request, "/", http.StatusFound)

		}
	}

}
func UpdateReader(x int) Animals {
	item := make([]Animals, 0)
	db, err := sql.Open("sqlite3", "./animals.sqlite")
	exitIfNil(err)
	defer db.Close()
	cmd := "SELECT * FROM animals WHERE id = $1"
	rows, err := db.Query(cmd, x)
	defer rows.Close()
	exitIfNil(err)
	for rows.Next() {
		var id int
		var name string
		var age int
		var Uid int
		err := rows.Scan(&id, &name, &age, &Uid)
		exitIfNil(err)
		item = append(item, Animals{Id: id, Name: name, Age: age})
	}
	return item[0]

}
func update(writer http.ResponseWriter, request *http.Request) {

	if request.Method == http.MethodGet {
		idReader, err := strconv.Atoi(request.URL.Query()["id"][0])
		exitIfNil(err)

		html, err := template.ParseFiles("update.html")
		html.Execute(writer, UpdateReader(idReader))
		fmt.Println("update is working")

	}
	if request.Method == http.MethodPost {

		err := request.ParseForm()
		exitIfNil(err)
		name := request.PostFormValue("name")
		age, err := strconv.Atoi(request.PostFormValue("age"))
		id, err := strconv.Atoi(request.PostFormValue("id"))
		exitIfNil(err)
		db, err := sql.Open("sqlite3", "./animals.sqlite")
		exitIfNil(err)
		defer db.Close()
		cmd := "UPDATE animals SET name = ?, age = ?, Uid = ? WHERE id = ?"
		db.Exec(cmd, name, age, globalUser, id)
		http.Redirect(writer, request, "http://localhost:5000/read", http.StatusFound)

	}

}
func logout(writer http.ResponseWriter, request *http.Request) {

	session, _ := store.Get(request, "cookie-name")
	auth, ok := session.Values["authenticated"].(bool)
	if !ok || !auth || globalUser == -1 {
		http.Redirect(writer, request, "/login", http.StatusFound)
	} else {
		if request.Method == http.MethodGet {

			html, err := template.ParseFiles("logout.html")
			exitIfNil(err)
			html.Execute(writer, nil)
			fmt.Println("logout is working")
		}
		if request.Method == http.MethodPost {
			session, _ := store.Get(request, "cookie-name")
			session.Values["authenticated"] = false
			globalUser = -1
			fmt.Println(globalUser)
			session.Save(request, writer)
			http.Redirect(writer, request, "/", http.StatusFound)
		}

	}

}
func delete(writer http.ResponseWriter, request *http.Request) {

	idReader, err := strconv.Atoi(request.URL.Query()["id"][0])
	exitIfNil(err)
	db, err := sql.Open("sqlite3", "./animals.sqlite")
	exitIfNil(err)
	defer db.Close()
	_, del := db.Exec("DELETE FROM animals WHERE id = $1", idReader)
	exitIfNil(del)
	http.Redirect(writer, request, "http://localhost:5000/read", http.StatusFound)

}

func main() {
	globalUser = -1
	http.HandleFunc("/", home)
	http.HandleFunc("/create", create)
	http.HandleFunc("/read", read)
	http.HandleFunc("/user", user)
	http.HandleFunc("/login", login)
	http.HandleFunc("/update", update)
	http.HandleFunc("/logout", logout)
	http.HandleFunc("/delete", delete)

	connection := http.ListenAndServe("localhost:5000", nil)
	log.Fatal(connection)
}
