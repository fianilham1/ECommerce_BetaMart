import React, { Component } from 'react';
import '../inputType.css';
import { Input } from '../../component';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUser, faUnlockAlt, faClipboardCheck, faEnvelope } from '@fortawesome/free-solid-svg-icons'
import Swal from 'sweetalert2'
// import withReactContent from 'sweetalert2-react-content'

const envelope = <FontAwesomeIcon icon={faEnvelope} />
const unlock = <FontAwesomeIcon icon={faUnlockAlt} />
const person = <FontAwesomeIcon icon={faUser} />
const check = <FontAwesomeIcon icon={faClipboardCheck} />

class Form extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isFocusName:false,
            isFocusUsername:false,
            isFocusPassword:false,
            isFocusConfirmPassword:false,
            isFocusRole:true,
            isFocusMainSalary:false,
            isFocusFood:false,
            isFocusTransport:false,
            isFocusEntertaint:false,
            roleInput:'Manager',
            userNew:{
                name:"",
                username:"",
                password:"",
                confirmpassword:"",
                role:"",
                mainsalary:0,
                allowance:{
                  food:0,
                  transport:0,
                  entertaint:0
                }

            }
        }
    }

    componentDidMount() {
        const { editStatus } = this.props;
        if (editStatus){
            this.setState({
                isFocusMainSalary:true,
                isFocusFood:true,
                isFocusTransport:true,
                isFocusEntertaint:true
            })
        }
      }

    onSubmitHandler =  e => {
        e.preventDefault();
        const {editStatus, edittedUser} = this.props; 

        if(!editStatus){ //for Register NEW USER
            let userInputNew = {
                [e.target[0].name]:e.target[0].value, //name
                [e.target[1].name]:e.target[1].value, //username
                [e.target[2].name]:e.target[2].value, //password
                [e.target[3].name]:e.target[3].value, //confirm pass
                [e.target[4].name]:e.target[4].value, //role
                [e.target[5].name]:parseInt(e.target[5].value), //main salary
                allowance:{
                    [e.target[6].name]:parseInt(e.target[6].value), //entertaint/food
                    [e.target[7].name]:parseInt(e.target[7].value) //transport
                }

            }
            console.log("NEWuser",userInputNew)
            
            if(userInputNew[e.target[2].name] === userInputNew[e.target[3].name]){
                this.setState({ userNew: userInputNew});
                this.props.onAddNewUser(userInputNew);
                return Swal.fire({
                        icon: 'success',
                        title: 'Register is Success',
                        showConfirmButton: false,
                        timer: 1500
                    })
            }else{
               return Swal.fire({
                        icon: 'error',
                        title: 'Password is Not Match',
                        text: 'Please try again!',
                        showConfirmButton: false,
                        timer: 1500
                    })
            }

        } //for EDIT SALARY

            let salaryInputNew = {
                id:edittedUser.id,
                [e.target[0].name]:parseInt(e.target[0].value),     //main Salary
                allowance:{                                         //if Manager   || if else
                    [e.target[1].name]:parseInt(e.target[1].value), //Entertaint   || food
                    [e.target[2].name]:parseInt(e.target[2].value), //  xxx        || transport
                }
            }
           
            this.props.onEditUser(salaryInputNew);
            return Swal.fire({
                    icon: 'success',
                    title: 'Edit Salary is Success',
                    showConfirmButton: false,
                    timer: 1500
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

    handleChange = e => {
        this.setState({
            roleInput:e.target.value
        })
    }

    renderAllowanceBasedRole = () => {
       
        if(this.state.roleInput!=="Manager") return (
            <>
            <Input focusState={this.state} name="Food" focus={this.focusHandler} blur={this.blurHandler} icon={check} typeTx="text"/>

            <Input focusState={this.state} name="Transport" focus={this.focusHandler} blur={this.blurHandler} icon={check} typeTx="text"/>
            </>
        )

        return <Input focusState={this.state} name="Entertaint" focus={this.focusHandler} blur={this.blurHandler} icon={check} typeTx="text"/>
    }


    renderPage = () => {
        const {editStatus, edittedUser} = this.props; 
        console.log("EDITSTATUS in FORM??",editStatus)

        if(editStatus && edittedUser.role==="Manager") return (
            <>
            <h2>Edit Salary For {edittedUser.name}</h2>

                <Input focusState={this.state} name="MainSalary" focus={this.focusHandler} blur={this.blurHandler} icon={check} typeTx="text" defaultValue={edittedUser.mainSalary}/>

                <Input focusState={this.state} name="Entertaint" focus={this.focusHandler} blur={this.blurHandler} icon={check} typeTx="text" defaultValue={edittedUser.allowance.entertaint}/>
                
                <button className="button" type="submit">Save</button>
            </>
        )

        if(editStatus) return (
            <>
            <h2>Edit Salary For {edittedUser.name}</h2>

                <Input focusState={this.state} name="MainSalary" focus={this.focusHandler} blur={this.blurHandler} icon={check} typeTx="text" defaultValue={edittedUser.mainSalary}/>

                <Input focusState={this.state} name="Food" focus={this.focusHandler} blur={this.blurHandler} icon={check} typeTx="text" defaultValue={edittedUser.allowance.food}/>

                <Input focusState={this.state} name="Transport" focus={this.focusHandler} blur={this.blurHandler} icon={check} typeTx="text" defaultValue={edittedUser.allowance.transport}/>
                
                <button className="button" type="submit">Save</button>
            </>
        )

        return (
            <>
            <h2>Create Account</h2>

                <Input focusState={this.state} name="Name" focus={this.focusHandler} blur={this.blurHandler} icon={person} typeTx="text" />

                <Input focusState={this.state} name="Username" focus={this.focusHandler} blur={this.blurHandler} icon={envelope} typeTx="text" />

                <Input focusState={this.state} name="Password" focus={this.focusHandler} blur={this.blurHandler} icon={unlock} typeTx="password" />

                <Input focusState={this.state} name="ConfirmPassword" focus={this.focusHandler} blur={this.blurHandler} icon={check} typeTx="password"/>

                <Input focusState={this.state} name="Role" focus={this.focusHandler} blur={this.blurHandler} icon={check} typeTx="text" typeInput="select" handleChange={this.handleChange}/>

                <Input focusState={this.state} name="MainSalary" focus={this.focusHandler} blur={this.blurHandler} icon={check} typeTx="text"/>

                {this.renderAllowanceBasedRole()}
                
                <button className="button" type="submit">Register</button>
            </>
        )

    }

    render() {
        const {editStatus} = this.props; 
        return (
            <React.Fragment>
            <form onSubmit={this.onSubmitHandler} className={`${!editStatus ? 'bgform':'bgform edit'}`}>
                {this.renderPage()}
                
            </form>
            
        </React.Fragment>
        );
    }
}

export default Form;