
import './Todo.css';

import React, { Component } from 'react';

class ShowEntries extends Component {
    constructor(props) {
        super(props);
        this.state = {  
            selectValue :4
        }
        this.handleDropdownChange = this.handleDropdownChange.bind(this);
    }

    handleDropdownChange(e) {
        this.setState({ selectValue: e.target.value });
        this.props.onSelectEntries(e.target.value); 
        console.log("cekEntries:",e.target.value)  
      }
    render() { 
       
        
        return ( 
            <React.Fragment>
            <div className="entries">Show
                <select className="entry" onChange={this.handleDropdownChange}>
                    <option value="4">4</option>
                    <option value="8">8</option>
                </select>entries
            </div>
            {/* <div>Selected value is : {this.state.selectValue}</div> */}
            </React.Fragment>
         );
        
    }
}
 
export default ShowEntries;