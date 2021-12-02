import React, { Component } from 'react';
import './cell.css';

class CellTable extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    renderButton = () => {
        const { dataId, onEditEvent, onDetailEvent, buttonName, totalCurrentRowPage, addClassType,loggedUser} = this.props;
        if (addClassType==="action") return (
            buttonName.map((button,index) => {
                if(index===0 && loggedUser.role==="HRD") return <button key={index} name={totalCurrentRowPage} data-id={dataId} id={`${button.toLowerCase()}Button`} onClick={onEditEvent}>{button}</button>

                if(index===1) return <button key={index} name={totalCurrentRowPage} data-id={dataId} id={`${button.toLowerCase()}Button`} onClick={onDetailEvent}>{button}</button>

                return''
                }
            )
        )

    }
    render() { 
        const {data, number, addClassType} = this.props;

        return ( 
            <div className={`cell ${addClassType ? addClassType : ''}`}>
                {number && addClassType==="num" ? number : data}
                {this.renderButton()}
            </div>
         );
    }
}
 
export default CellTable;