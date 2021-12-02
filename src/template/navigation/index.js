import React, { Component } from "react";
import "./nav.css";
import { Link } from "react-router-dom"
import { connect } from 'react-redux';
import carpark from "./car-park.png"
import Swal from 'sweetalert2'
import { FirebaseContext } from '../../config/firebase';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUser, faHome, faBars, faTimes, faSignInAlt, faSignOutAlt, faCar, faList} from '@fortawesome/free-solid-svg-icons';

const person = <FontAwesomeIcon className="person" icon={faUser} />
const signout = <FontAwesomeIcon className="signout" icon={faSignOutAlt} />
const signin = <FontAwesomeIcon className="signin" icon={faSignInAlt} />
const home = <FontAwesomeIcon className="home" icon={faHome} />
const car = <FontAwesomeIcon className="car" icon={faCar} />
const list = <FontAwesomeIcon className="list" icon={faList} />
const bar = <FontAwesomeIcon icon={faBars} />
const times = <FontAwesomeIcon icon={faTimes} />

class NavFirebase extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isLogin:false,
      openMenu:false
    };
  }

  componentDidMount(){
    this.props.firebase.currentLoggedFirebaseUser(user=>{
      if(user) {
        this.setState({
          isLogin:true
        })

      }else{

      }
    })
  }

  logoutHandler = e => {
   
    this.props.firebase
    .logoutFirebaseUser()
    .then(res => 
      this.props.changePage("/login"),
        Swal.fire({
            icon: 'success',
            title: 'Logout',
            showConfirmButton: false,
            timer: 1500
          }),
          this.setState({
            isLogin:false
          })
        )
    .catch(err => 
        Swal.fire({
            icon: 'error',
            title: err.message,
            showConfirmButton: false,
            timer: 1500
          })
        )

  }

  renderWelcome = () => {
    
    if(this.state.isLogin) return <div className="welcome"> Welcome, <span className="nameWelcome">nama?</span><div className="nameWelcome">nama?</div> </div>
  
    return ''
  }


  renderLoggedNav = () => {
    const {currentPage} = this.props

    if(this.state.isLogin) return (
      <Link to="/login">
      <div onClick={this.logoutHandler} className={`menu-item ${currentPage === "/logout" ? "active" : ""}`}>
        <span className="header-i"> {signout}</span>Logout
      </div>
      </Link>
    )

    return (
      <>
      <Link to="/login">
        <div className={`menu-item ${currentPage === "/login" ? "active" : ""}`}>
          <span className="header-i"> {signin}</span>Login
        </div>
      </Link>
      </>
    )
  }

  renderNav = () => {
    const {currentPage} = this.props
    if(this.state.isLogin) return (
        <>
                  <Link to="/dashboard">
                    <div className={`menu-item ${currentPage === "/dashboard" ? "active" : ""}`}>
                      <span className="header-i"> {home}</span> Home
                    </div>
                  </Link>
                  <Link to="/check-in-park">
                    <div className={`menu-item ${currentPage === "/check-in-park" ? "active" : ""}`}>
                      <span className="header-i"> {car}</span>Check In Parking
                    </div>
                  </Link> 
                  <Link to="/list-parking">
                    <div className={`menu-item ${currentPage === "/list-parking" ? "active" : ""}`}>
                      <span className="header-i"> {list}</span>List Booking Park
                    </div>
                  </Link>
                    {/* <Link to="/sign-up">
                    <div className={`menu-item ${currentPage === "/sign-up" ? "active" : ""}`}>
                      <span className="header-i"> {person}</span>Sign Up
                    </div>
                  </Link> */}
                
        </>
      )

    return ''
  }

  menu = () => {
    this.setState({
      openMenu:!this.state.openMenu
    })
  }

    render() {
      console.log(this.state.isLogin)
        return (
          <>
          <div className="header">
          <div className="inner-width">
                <div className="logo">
                  <img className=".home" src={carpark} alt=""/>
                </div>
                <span className="menu-toggle-btn" onClick={this.menu}> {this.state.openMenu ? times : bar}</span>
                <div className={`navigation-menu ${this.state.openMenu ? 'open': ''}`}>
                {this.renderLoggedNav()}
                {this.renderNav()}
                </div>
              </div>
          </div>
          
          </>
        );
    }
}

class Nav extends Component {
  constructor(props) {
    super(props);
    this.state = {  }
  }
  render() { 
    return ( 
      <FirebaseContext.Consumer>
      {firebase => <NavFirebase {...this.props} firebase={firebase} />}
      </FirebaseContext.Consumer>
     );
  }
}

const mapStateToProps = state => ({
  currentPage: state.pageConfig.currentPage,
})

const mapDispatchToProps = dispatch => ({
  changePage: page => dispatch({ type: page })
})

// export default Detail;
export default connect(mapStateToProps,mapDispatchToProps)(Nav);
