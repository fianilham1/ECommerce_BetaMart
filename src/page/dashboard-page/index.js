import React, { Component } from 'react';
import "./dashboard.css"
import { FirebaseContext } from '../../config/firebase';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';

class DashboardFirebase extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            isLogin:false
         }
    }

    componentDidMount(){
        this.props.changePage("/dashboard")
        this.props.firebase.currentLoggedFirebaseUser(user=>{
            if(user) {
              this.setState({
                isLogin:true
              })
            }else{
              
            }
          })
    }

    componentWillUnmount(){
        this.setState({
            isLogin:false
        })
    }
    render() { 
        // if (!this.state.isLogin)
        // return <Redirect to="/login" />

        return ( 
            <>
            <div className="home-container">
                <div></div>
                <h1 className="welcome-home">WELCOME</h1>
            </div>
            </>
         );
    }
}

class Dashboard extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    render() { 
        return ( 
            <FirebaseContext.Consumer>
            {firebase => <DashboardFirebase {...this.props} firebase={firebase} />}
            </FirebaseContext.Consumer>
         );
    }
} 


const mapDispatchToProps = dispatch => ({
    changePage: page => dispatch({ type: page })
})

export default connect(null, mapDispatchToProps)(Dashboard);