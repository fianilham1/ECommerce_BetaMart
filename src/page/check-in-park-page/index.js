import React, { Component } from 'react';
import "./check-in-park.css"
import { Input, Dialog } from '../../component';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCar, faCalendarAlt, faClock} from '@fortawesome/free-solid-svg-icons'
import IconButton from '@material-ui/core/IconButton';
import PhotoCameraIcon from '@material-ui/icons/PhotoCamera';
import Swal from 'sweetalert2'
import { Redirect} from 'react-router-dom'
import { connect } from 'react-redux';
import { FirebaseContext } from '../../config/firebase';

const car = <FontAwesomeIcon icon={faCar} />
const date = <FontAwesomeIcon icon={faCalendarAlt} />
const time = <FontAwesomeIcon icon={faClock} />

class BookParkFirebase extends Component {
    constructor(props) {
        super(props);

        this.state = {
            // id: props.selectedUser.id ? props.selectedUser.id : "",
            // name: props.selectedUser.name ? props.selectedUser.name : "",
            // address: props.selectedUser.address ? props.selectedUser.address : ""
            photo:'',
            getDoc:'',
            isLogin:false,
            submitStatus:false,
            isFocusCarMerk:false, 
            isFocusCarPlate:false,
            isFocusDateIn:true,
            isFocusTimeIn:true,
            carmerk:'',
            carplate:'',
            datein:'',
            timein:''
        }
        this.baseSate=this.state  
    }

    componentDidMount(){
        this.props.changePage("/check-in-park")
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
        this.resetSubmitStatus()
        this.setState({
            isLogin:false
        })
    }

    takePhoto = () => {
        const photoArr = ["https://www.caranalytics.co.uk/guides/wp-content/uploads/2020/09/find-car-owner-by-registration-number.jpg","https://cms.webuyanycar.com/cmsmedia/images/private-plate.jpg","https://s1.paultan.org/image/2020/01/UTM-plate-Malaysia-1.jpg"]
        const randomNum = Math.floor(Math.random() * photoArr.length) + 1
        this.setState({
            photo:photoArr[randomNum-1],
            datein: this.dateNow(),
            timein: this.timeNow()
        })
    }

    resetSubmitStatus = () => {
        this.setState(this.baseSate)
    }

    setValue = e => {
        this.setState({ 
        [e.target.name]: e.target.value,
        submitStatus:false
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

    resetForm = () => {
        this.setState(this.baseSate)
    }

    getDataByDoc = docName => {
        this.props.firebase.crudFirestore().doc(docName).get().then((doc) => {
            if (doc.exists) {
                console.log("Document data:", doc.data());
                this.setState({
                    getDoc:doc.data()
                })
            } else {
                // doc.data() will be undefined in this case
                console.log("No such document!");
            }
        }).catch((error) => {
            console.log("Error getting document:", error);
        });
    }

    onSubmitHandler =  e => {
        e.preventDefault();
      
        this.setState({
            submitStatus:true
        })
        this.resetSubmitStatus()
        const {carmerk, carplate, datein, timein, photo} = this.state

        if (carmerk !== "" && carplate !== "" && datein !== "" && timein !== "") {
            // this.getDataByDoc()
            this.props.firebase.crudFirestore().add({
                carmerk,
                carplate,
                datein,
                timein,
                price:0,
                photo,
                dateout:'',
                timeout:'',
                duration:'',
                status:"progress"
            })
            .then((docRef) => {
                Swal.fire({
                    icon: 'success',
                    title: 'Booking success',
                    showConfirmButton: false,
                    timer: 1500
                  })
            })
            .catch((error) => {
                Swal.fire({
                    icon: 'error',
                    title: error.message,
                    showConfirmButton: false,
                    timer: 1500
                  })
            });
            
        } else Swal.fire({
            icon: 'error',
            title: 'Fill All Fields',
            showConfirmButton: false,
            timer: 1500
          })
      }

    dateNow = () => {
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth()+1; //January is 0!
        var yyyy = today.getFullYear();
        if(dd<10){
            dd='0'+dd;
        } 
        if(mm<10){
            mm='0'+mm;
        } 
       return yyyy+'-'+mm+'-'+dd; 
    }

    timeNow = () => {
        const randomHours = Math.floor(Math.random() * 4) + 1
        var today = new Date();
        var HH = today.getHours() - randomHours;
        var MM = today.getMinutes();
        if(HH<10){
            HH='0'+HH;
        } 
        if(MM<10){
            MM='0'+MM;
        } 
        return HH+':'+MM
    }

    renderPhoto = () => {

        if(this.state.photo==='') return <div className="car-photo">car photo</div>

        return ( <img className="car-photo" src={this.state.photo} alt=""/>)
    }

    
    render() {
        // if (!this.state.isLogin)
        // return <Redirect to="/login" />

        if(this.state.submitStatus) return <Redirect to="/list-parking" />


        return (

            <div className="bg">          
             <div className="bgform">
             <h2 className="titleBook" align="center">Check In Parking</h2>
             <Input 
                    state={this.state} 
                    name="CarMerk" 
                    label="Cark Merk"
                    focus={this.focusHandler} 
                    blur={this.blurHandler} 
                    icon={car} 
                    typeTx="text" 
                    placeholder="ex: Honda"
                    handleChange={this.setValue}
                    submitStatus={this.state.submitStatus}/> 

                <Input 
                    state={this.state} 
                    name="CarPlate" 
                    label="Cark Plate"
                    focus={this.focusHandler} 
                    blur={this.blurHandler} 
                    icon={car} 
                    typeTx="text" 
                    placeholder="ex: W 2255 AG"
                    handleChange={this.setValue}
                    submitStatus={this.state.submitStatus}/>   

                <Input 
                    state={this.state} 
                    name="DateIn" 
                    label="Date In"
                    focus={this.focusHandler} 
                    icon={date} 
                    typeTx="date"
                    fixValue={this.state.datein}
                    handleChange={this.setValue}
                    submitStatus={this.state.submitStatus}/>

                <Input 
                    state={this.state} 
                    name="TimeIn" 
                    label="Time In"
                    focus={this.focusHandler} 
                    icon={time} 
                    typeTx="time" 
                    fixValue={this.state.timein} 
                    handleChange={this.setValue}
                    submitStatus={this.state.submitStatus}/>

                <div className="photo-container">
                    <div>
                        {this.renderPhoto()}
                    </div>
            
                    <div>
                        <IconButton 
                        className="take-photo" 
                        onClick={this.takePhoto}>
                            Take 
                            <PhotoCameraIcon  fontSize="large" />
                        </IconButton>
                    </div>
                </div>
              
            <button className="bookButton" onClick={this.onSubmitHandler}> Check In Parking </button>
    
            </div>        
           
        </div>
        );
    }
}

class BookPark extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    render() { 
        return ( 
            <FirebaseContext.Consumer>
            {firebase => <BookParkFirebase {...this.props} firebase={firebase} />}
            </FirebaseContext.Consumer>
         );
    }
}
 
// const mapStateToProps = state => ({
//     isLogedIn: state.Auth.statusLogin,
//     userLogin: state.Auth.userLogin
// })

const mapDispatchToProps = dispatch => ({
    changePage: page => dispatch({ type: page })
})

export default connect(null, mapDispatchToProps)(BookPark);
