import React, { Component } from 'react';
import { Login, Form, UserList, Detail } from '../../page';


class Body extends Component {
    constructor(props) {
        super(props);
        this.state = {
            edittedUser:{},
            detailUser: {},
            userList: [
                // {
                //     id: 1,
                //     name: 'Bobo',
                //     username: 'bobo1@gmail.com',
                //     password:'bobo123@',
                //     role: 'HRD',
                //     mainSalary:7000000,
                //     allowance:{
                //       food:1000000,
                //       transport:1200000
                //     }
                // },
                // {
                //   id: 2,
                //   name: 'Fian',
                //   username: 'fian1@gmail.com',
                //   password:'fian123@',
                //   role: 'Manager',
                //   mainSalary:10000000,
                //   allowance:{
                //     entertaint:2500000
                //   }
                // },
                // {
                //   id: 3,
                //   name: 'Steve',
                //   username: 'steve1@gmail.com',
                //   password:'steve123@',
                //   role: 'Employee',
                //   mainSalary:5000000,
                //   allowance:{
                //     food:1500000,
                //     transport:1500000
                //   }
                // },
                // {
                //   id: 4,
                //   name: 'Hahi',
                //   username: 'hahi1@gmail.com',
                //   password:'hahi123@',
                //   role: 'Employee',
                //   mainSalary:5000000,
                //   allowance:{
                //     food:1500000,
                //     transport:1500000
                //   }
                // },
                // {
                //   id: 5,
                //   name: 'John',
                //   username: 'john1@gmail.com',
                //   password:'john123@',
                //   role: 'Employee',
                //   mainSalary:5000000,
                //   allowance:{
                //     food:1500000,
                //     transport:1500000
                //   }
                // }
            ],
            loading:false,
        }
    }

    getAPI = () => {
      const urlFetch = fetch('http://localhost:3000/posts') 
      //Use= npm json server -> file of list user in: src/server/db.json
      urlFetch.then( res => {
        if(res.status === 200)
            return res.json()   
      }).then( resJson => {
          // console.log("JSONDATA:",resJson)
       this.setState({
         userList:resJson
       })
      }).finally(() => this.setState({ loading: false }))
    }

    componentDidMount() {
      if (!this.state.userList.length)
        setTimeout(() => { //simulation for slow network (delay)
          this.getAPI()
        }, 3000);
          
    }
    
      addNewUserHandler = newUser => {
        let userCopy = this.state.userList;
        let userInputNew = {
            id: userCopy[userCopy.length-1].id+1,
            name: newUser.name,
            username: newUser.username,
            password: newUser.password,
            mainSalary: newUser.mainsalary,
            role: newUser.role,
            allowance:{
              food: newUser.allowance.food,
              transport: newUser.allowance.transport,
              entertaint: newUser.allowance.entertaint
            }
        }

        userCopy.push(userInputNew)
    
        this.setState({ userList: userCopy});
        this.props.goToPage("userList")
        console.log("call add new in MAIN LIST:",userInputNew)
      }

      // deleteUserHandler = deletedRow => {
      //   let userCopy = JSON.parse(JSON.stringify(this.state.userList))
      //   userCopy.splice(deletedRow-1, 1)
       
      //   this.setState({ userList: userCopy});
      // }

      editUserHandler = edittedSalary => {
        const userCopy = this.state.userList;
        const indexForEdit = userCopy.map((user)=> {return user.id}).indexOf(edittedSalary.id);

        console.log("SALARY1",userCopy[indexForEdit].mainSalary)
        userCopy[indexForEdit].mainSalary = edittedSalary.mainsalary;
        userCopy[indexForEdit].allowance.food = edittedSalary.allowance.food;
        userCopy[indexForEdit].allowance.transport = edittedSalary.allowance.transport;
        userCopy[indexForEdit].allowance.entertaint = edittedSalary.allowance.entertaint;
       
        this.setState({ 
          userList: userCopy,
        });

        console.log("SALARY2",userCopy[indexForEdit].mainSalary)
        this.props.goToPage("userList")
      }

      editEventHandler = id => {
        const user = this.state.userList[id-1]
        this.setState({
          edittedUser:user
        })
        this.props.goToPage("form")
        this.props.onEditEvent(true) //to set true
      }

      detailEventHandler = id => {
        const user = this.state.userList[id-1]
        this.setState({
          detailUser:user
        })
        this.props.goToPage("detail")
      }


    renderPage = () => {
        const {loggedUser, page, onLogin, editStatus, goToPage} = this.props;
        console.log("CURRENT detail:",this.state.detailUser)

        const filteredUserBasedRole = []; 

        if (loggedUser){
          this.state.userList.map((user,index) => {
            if(loggedUser.role==='HRD'){
              filteredUserBasedRole.push(user)
              return index
            }
      
            if(loggedUser.role==='Manager' && (user.username===loggedUser.username || user.role==='Employee')){
              filteredUserBasedRole.push(user)
              return index
            }

            if(loggedUser.role==='Employee' && user.username===loggedUser.username){
              filteredUserBasedRole.push(user)
              return index
            }
      
            return ''
          })
  
        }
        
        if (page === "form")
            return <Form onAddNewUser={this.addNewUserHandler} loggedUser={loggedUser} editStatus={editStatus} onEditUser={this.editUserHandler} edittedUser={this.state.edittedUser} />

        if (page === "userList")
            return <UserList setUsers={this.setUsers} dataUser={filteredUserBasedRole} loggedUser={loggedUser} onEditEvent={this.editEventHandler} onDetailEvent={this.detailEventHandler}/>

        if (page === "detail")
            return <Detail detailUser={loggedUser.role!=="Employee" ? this.state.detailUser : filteredUserBasedRole[0]} goToPage={goToPage} loggedUser={loggedUser}/>

        return <Login onLogin={onLogin} dataUser={this.state.userList}/>
    }

    render() {
        console.log("currentUSERLIST",this.state.userList)
        return (
            <div className="body">
                {this.renderPage()}
            </div>
        );
    }
}

export default Body;