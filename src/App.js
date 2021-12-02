import React, { Component } from 'react';
import { Header, Nav, Body } from './template';
import "./App.css"
import Swal from 'sweetalert2'
// import withReactContent from 'sweetalert2-react-content'


class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loginStatus:false,
      editStatus:false,
      idEdit:-1,
      currentPage: "login",
      currentLoggedUser: {
        username: "",
        password: "",
        role:""
      }
    }
  }

  changePage = page => {
    if (page === "logout"){
      const clearLoggedUser = {
        username: "",
        password: "",
        role:""
      }
      this.setState({
        loginStatus: false,
        editStatus: false,
        currentLoggedUser:clearLoggedUser,
        currentPage: "login"
      })
      return Swal.fire({
        icon: 'success',
        title: 'Logout',
        showConfirmButton: false,
        timer: 1500
      })
    }
    if(page === "userList"){
      this.setState({
        editStatus: false
      }) 
    }
    this.setState({
      currentPage: page
    })
  }

  loginHandler = loggedUser => {

   this.setState({
      loginStatus:true,
      currentLoggedUser:loggedUser,
      currentPage: `${loggedUser.role!=="Employee" ? 'userList':'detail'}`
    })
  //  this.setState({currentLoggedUser:loggedUser})
   console.log("current logged User",loggedUser)
  }

  editStatusHandler = status => {
    this.setState({
      editStatus:status
    })
  }

  // updateLoggedHandler = edittedLoggedUser => {
  //   this.setState({
  //     currentLoggedUser:edittedLoggedUser})
  // }

  // GoToEditFormHandler = () => {
  //   this.setState({
  //     currentPage: "register",
  //     editStatus:true
  //   })
  // }

  // editStatusHandler = id => {
  //   this.setState({
  //     currentPage: "form",
  //     editStatus:true,
  //     idEdit:id
  //   })
  // }

  render() {
    console.log("LOGGED",this.state.currentLoggedUser)
    console.log("logging status",this.state.loginStatus)
    return (
      <>
        <Header />
        <Nav page={this.state.currentPage} goToPage={this.changePage} loginStatus={this.state.loginStatus} loggedUser={this.state.currentLoggedUser} editStatus={this.state.editStatus}/>
        <Body page={this.state.currentPage} onLogin={this.loginHandler} loginStatus={this.state.loginStatus} loggedUser={this.state.currentLoggedUser} onEditEvent={this.editStatusHandler} editStatus={this.state.editStatus} goToPage={this.changePage}/>
        {/* <button onClick={() => this.changePage("about")}>Change</button> */}
      </>
    )
  }
}

export default App;


/**
 * ======== KONSEP NAVIGATION ========
 * App: <= buat state disini
 *    - Nav:
 *          - Menu
 *    - Body
 */
