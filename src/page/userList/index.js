import React, { Component } from 'react';
import './Todo.css';
import Table from './Table';
import ShowEntries from './ShowEntries';

class About extends Component {
    constructor(props) {
        super(props);
        this.state = {
            pageConfig : {
                currentEntries:4,
                currentPage:1
              },
            isAddNew : false,
            user : [],
        }
    }

    handleEntries = entries => {
        let pageConfigCopy = JSON.parse(JSON.stringify(this.state.pageConfig))
        pageConfigCopy.currentEntries = parseInt(entries)
       
        this.setState({ pageConfig: pageConfigCopy});
        console.log("call entries:",pageConfigCopy)
        // console.log("call entries:",this.state.pageConfig) 
      }
    handlePage = page => {
        let pageConfigCopy = JSON.parse(JSON.stringify(this.state.pageConfig))
        pageConfigCopy.currentPage = parseInt(page)
       
        this.setState({ pageConfig: pageConfigCopy});
        console.log("call page in userList:",page)
      }

    render() {
        const {dataUser, loggedUser, onEditEvent, onDetailEvent} = this.props;
        return (
            <React.Fragment>
              <div className="bgTable">
                <h2>List of Users</h2>
                <div className="featureAddSearch">
                  {/* <div>
                    <button id="addButton" onClick={this.handleClickAddData}>Add New</button>
                  </div> */}
                  <div className="searchbg">
                    <input type="text" className="searhInput" onKeyUp="Search()" placeholder="Search..." title="Type in a name"/>
                  </div>
                </div>
        
                <Table pageConfig={this.state.pageConfig} dataUser={dataUser} onSelectPage={this.handlePage} loggedUser={loggedUser} onEditEvent={onEditEvent} onDetailEvent={onDetailEvent}  />
        
                <ShowEntries onSelectEntries={this.handleEntries}/>
        
              </div>
            </React.Fragment>
        );
    }
}

export default About;