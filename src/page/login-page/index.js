import React, { Component } from 'react';
// import "./register.css"
import Swal from 'sweetalert2'
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';
import "./login.css"
import { FirebaseContext } from '../../config/firebase';

class LoginPageFirebase extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isLogin:false,
            serviceStatus:'',
            submitStatus:false,
            isFocusUsername:false,
            isFocusPassword:false,
            username:'',
            password:'',
            checkApi:false
          
        }
        this.baseSate=this.state
       
    }

    componentDidMount(){
        this.props.changePage("/login")
        this.props.firebase.currentLoggedFirebaseUser(user=>{
            if(user) {
              this.setState({
                isLogin:true
              })
            }else{
                this.setState({
                    isLogin:false
                })
            }
          })

        // if(this.props.userList.length===0){
        //     this.triggerRender()
        // }
    }

    triggerRender = () => {
        this.setState({
            serviceStatus:"loading"
        })
        console.log("** delay re render of current page....")
        setTimeout(() => { 
            this.setState({
                serviceStatus:"reRender"
            })
            console.log("** delay re render DONE....")
        }, 10000);
      
      }

    setValue = e => {
        this.setState({ 
        [e.target.name]: e.target.value
     })
    }

    focusHandler = e => {
        this.setState({[e.target.id]:true})
    }  
    blurHandler = e => {
        const value = e.target.value;
        if(value===''){
            this.setState({[e.target.id]:false})
        }       
    }

    resetState = () => {
        this.setState(this.baseSate)
    }

    onSubmitHandler =  e => {
        e.preventDefault();

        const userLogin = {
            id:1,
            name:"Fian",
            username:this.state.username,
            role:"Admin"
        }
        const {username, password} = this.state
        if (username !== "" && password !== "") {
            this.props.firebase
                .loginFirebaseUser({email:username, password})
                .then(res => 
                    Swal.fire({
                        icon: 'success',
                        title: 'Login Is Success',
                        showConfirmButton: false,
                        timer: 1500
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
        } else Swal.fire({
            icon: 'error',
            title: 'Fill All Fields',
            showConfirmButton: false,
            timer: 1500
          })

      }

    render() {
       
        if(this.state.isLogin) return <Redirect to="/dashboard" />
        
        return (
            <>
            <div className="wrapper login">
            <div className="container">
                <div className="col-left">
                    <div className="login-text">
                        <h2>Welcome To Wibu Park</h2>
                        <p>Please Sign In.<br/></p>
                        <div className="role-container">
                            <div className="btn role-active">Parking Officer</div>
                            <div className="btn">Admin</div>
                        </div>
                       
                    </div>
                </div>
    
                <div className="col-right">
                    <div className="login-form">
                        <h2>Login</h2>
                        <form action="">
                            <p>
                                <label>Username<span>*</span></label>
                                <input 
                                    type="text" 
                                    name="username"
                                    placeholder="Username" 
                                    onChange={this.setValue}
                                    required/>
                            </p>
                            <p>
                                <label>Password<span>*</span></label>
                                <input 
                                    type="password" 
                                    name="password"
                                    placeholder="Password" 
                                    onChange={this.setValue}
                                    required/>    
                            </p>
                            <p>
                                <input type="submit" onClick={this.onSubmitHandler} value="Sign In"/>
                            </p>
                            <p>
                                <div className="forget-pass-btn">Forget password?</div>
                            </p>
    
                        </form>
                    </div>
                </div>
    
            </div>
            </div>
            </>
            
        //     <div classNameName="bg">
        //      <h1 classNameName="titleRegister1" align="center">MALL OF WIBU</h1>
        //      <h2 classNameName="titleRegister" align="center">Login</h2>
        //      <form classNameName="bgform login">
        //      <Input 
        //             state={this.state} 
        //             name="Username" 
        //             label="Username"
        //             focus={this.focusHandler} 
        //             blur={this.blurHandler} 
        //             icon={envelope} 
        //             typeTx="email" 
        //             handleChange={this.setValue}
        //             submitStatus={this.state.submitStatus}/>

                // <Input 
                //     state={this.state} 
                //     name="Password" 
                //     label="password"
                //     focus={this.focusHandler} 
                //     blur={this.blurHandler} 
                //     icon={unlock} 
                //     typeTx="password" 
                //     handleChange={this.setValue}
                //     submitStatus={this.state.submitStatus}/>

        //         <button  type="submit" classNameName="submitButton" onClick={this.onSubmitHandler}>Sign in</button>

        //         <div classNameName={`lds-ring modal ${this.state.serviceStatus==="loading" ? "loading":''}`}><div></div><div></div><div></div></div>
        //     </form>        
        // </div> 
        );
    }
}

class LoginPage extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    render() { 
        return ( 
            <FirebaseContext.Consumer>
                {firebase => <LoginPageFirebase {...this.props} firebase={firebase} />}
            </FirebaseContext.Consumer>
         );
    }
}
 
// const mapStateToProps = state => ({
//     isLogedIn: state.Auth.statusLogin,
//     userList: state.UserList.users,
//     userLogin: state.Auth.userLogin
// })

const mapDispatchToProps = dispatch => ({
    changePage: page => dispatch({ type: page })
})

export default connect(null, mapDispatchToProps)(LoginPage);
