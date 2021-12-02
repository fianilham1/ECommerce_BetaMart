import React, { Component } from 'react';
import { Menu } from '../../component';
import "./nav.css"
// import Swal from 'sweetalert2'
// import withReactContent from 'sweetalert2-react-content'


class Nav extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isMouse:false,
            loggedUser : {
                name: '',
                username: '',
                password:''
              }
        }
    }

    componentDidMount() {
        const { loggedUser } = this.props;
        this.setState({loggedUser:loggedUser})
      }

    showUserListAfterLogin = () => { //for page UserList ONLY
        const {goToPage, loginStatus, loggedUser} = this.props;

        if (loginStatus && loggedUser.role!=="Employee"){
            return <Menu isActivePage={this.checkActivePage("userList")} redirect={() => goToPage("userList")}>User List</Menu>
        }

        return ''
      
    }

    showPageAfterLogin = () => { //for page login, logut, register/edit
        const {goToPage, loginStatus, loggedUser, editStatus} = this.props;
        console.log("Edit in nav",editStatus)
        if (loginStatus && loggedUser.role==="HRD")
        return <>
        <Menu isActivePage={this.checkActivePage("logout")} redirect={() => goToPage("logout")}>Logout</Menu>
        <Menu isActivePage={this.checkActivePage("form")} redirect={() => goToPage("form")}>{editStatus ? 'Edit':'Register'}</Menu>
        </>
        
        if (loginStatus)
        return <>
        <Menu isActivePage={this.checkActivePage("logout")} redirect={() => goToPage("logout")}>Logout</Menu>
        </>

        return <>
        <Menu isActivePage={this.checkActivePage("login")} redirect={() => goToPage("login")}>Login</Menu>
        </>        
    }
    getUser = () => {
        const {loggedUser, loginStatus} = this.props;
        if (loginStatus)
        return <div className="welcome">{`Welcome, ${String(loggedUser.name)} `}<h3 className="roleWelcome">{String(loggedUser.role)}</h3></div>

        return ''
    }

    checkActivePage = activePage => {
        const { page } = this.props
        if (activePage === page) return "active"
       
        return ""
    }

    render() {
        // console.log("navlogged",this.getLoggedUser)
        const { loggedUser } = this.props
        console.log("cekk",loggedUser)
        return (
            <>
            <div className="nav">
                {this.showPageAfterLogin()} 
                {this.showUserListAfterLogin()}
                {this.getUser()}
            </div>
            
            </>
            
        );
    }
}

export default Nav;

// <Menu isActivePage={this.checkActivePage("userList")} redirect={loginStatus ? () => goToPage("userList") : () =>  Swal.fire({
//                 icon: 'error',
//                 title: 'Please Sign In First',
//                 text: 'Please try again!',
//                 showConfirmButton: false,
//                 timer: 1500
//             })
//             }>User List</Menu>