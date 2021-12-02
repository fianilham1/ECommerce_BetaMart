
import './Todo.css';
import Pagination from './Pagination';
import { RowTable } from '../../component';

import React, { Component } from 'react';

const Header = () => {
    return ( 
    <div class="row">
        <div class="cell header">No</div>
        <div class="cell header">Name</div>
        <div class="cell header">Position</div>
        <div class="cell header">Username</div>
        <div class="cell header">Password</div>
        <div class="cell header">Total Salary</div>
        <div class="cell header">Salary Action</div>
    </div>
    );
};

class Table extends Component {
  constructor(props) {
    super(props);
    this.state = { 
        isEdit : {
          no:-1,
          status:false
        },
        isAddNew : false,
        data : {
        row: '',
        name: '',
        username: '',
        password:''
        },
        addData : {
          id: '',
          name: '',
          username: '',
          password:''
        },
        loggedUser : {
          name: '',
          username: '',
          password:'',
          role:''
        }
     }
   
  }

  componentDidMount() {
    const { loggedUser } = this.props;
    this.setState({loggedUser:loggedUser})
  }

  handlePage = val => {
    this.props.onSelectPage(val);
    console.log("call page in table:",val) 
  }

  handleEdit = e => {
      console.log("TARGET EDIT",e.target.attributes[0].value)
      console.log("LOGGED-IN-TABLE",this.state.loggedUser)

      this.props.onEditEvent(e.target.attributes[0].value);
      // console.log("Row-i:",e.target.parentElement.parentElement)
  }

  handleDetail = e => {
    console.log("TARGET DETAIL",e.target.attributes[0].value)
    this.props.onDetailEvent(e.target.attributes[0].value);
}
  
  
  // handleDelete = e => {
  //   const deletedRow = parseInt(e.target.className);
  //   const totalCurrentRowPage = parseInt(e.target.name);
  //   const { pageConfig, dataUser } = this.props;
  //   if(dataUser.length!==0){ //left header only
  //     console.log("CEK CURRENT PAGE BEFORE DELETE",pageConfig.currentPage)
  //     console.log("CEK BEFORE DELETE",totalCurrentRowPage)
  //     if(totalCurrentRowPage === 1 && pageConfig.currentPage!==1){ 
  //       pageConfig.currentPage -= 1;
  //     }
  
  //     this.props.onDelete(deletedRow);
  //     this.props.onSelectPage(pageConfig.currentPage);
  //   }
    
  // }
  handleChange = e => {
    // console.log(e.target.value)
    let property = `${e.target.name}`;
    this.setState(prevState => {
      let data = { ...prevState.data };    // creating copy of state variable 
      data[property] = `${e.target.value}`;// update the name property, assign a new value                 
      return { data };                     // return new object 
    })
  }


  render() { 
    const { pageConfig, dataUser } = this.props;

    let filteredData = []; //filter based on pagination
    let entries = pageConfig.currentEntries;
    let current = pageConfig.currentPage;
    let startIndex = (entries)*(current-1);
    let endIndex = (entries)+(entries)*(current-1);
    console.log("pageconfig",pageConfig)
    for(let i = startIndex;i < endIndex;i++){
        if(i<dataUser.length){ //limit to user data length
            const user = dataUser[i];
            filteredData.push(user);
        }        
    }
  
    startIndex++;
    console.log("ROLE",this.state.loggedUser.role)
    let showData = filteredData.map((data,index) => {

      return <RowTable keyNum={index} startIndex={startIndex} data={data} onEditEvent={this.handleEdit} onDetailEvent={this.handleDetail} buttonName={["Edit","Detail"]} loggedUser={this.state.loggedUser}/>
        
    });

     
    return ( 
      <React.Fragment>
        <div class="tbl">
          <Header />
          {showData}
        </div>

        <Pagination pageConfig={pageConfig} userList={dataUser} onSelectPage={this.handlePage}/>
      
      </React.Fragment>
     );
  }
}
 
export default Table;

// const eventAddNew = () => {
//   document.querySelector(".tbl").addEventListener('keyup', e => {
//       // console.log("e?",e)
//       if (e.key === "Enter") saveData()
//   })
// }
