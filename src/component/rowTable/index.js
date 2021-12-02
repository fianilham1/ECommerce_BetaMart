import React, { Component } from 'react';
import CellTable from '../cellTable';

class RowTable extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }

    render() { 
        const {data, keyNum, startIndex, onEditEvent, onDetailEvent, buttonName, totalCurrentRowPage, loggedUser} = this.props;
        const number = startIndex+keyNum;
        const totalSalary = data.allowance.entertaint ? data.mainSalary + data.allowance.entertaint : data.mainSalary + data.allowance.food + data.allowance.transport;
        return ( 
            <>
            <div key={number} class="row">
                <CellTable number={number} addClassType="num"/>
                <CellTable data={data.name}/>
                <CellTable data={data.role} />
                <CellTable data={data.username} />
                <CellTable data={data.password} addClassType="pass"/>
                <CellTable data={`Rp${totalSalary}`} />
                <CellTable loggedUser={loggedUser} dataId={data.id} totalCurrentRowPage={totalCurrentRowPage} onEditEvent={onEditEvent} onDetailEvent={onDetailEvent} buttonName={buttonName} addClassType="action"/>
                {/* <div className="cell num">{number}</div>
                <div className="cell">{data.name}</div>
                <div className="cell">{data.username}</div>
                <div className={`cell ${typeTx ? typeTx : ''}`}>{data.password}</div>
                <div className="cell action">
                    <button name={totalCurrentRowPage} className={number} id={`${text.toLowerCase()}Button`} onClick={onClickEvent}>{text}</button>
                </div> */}
            </div>
            </>
         );
    }
}
 
export default RowTable;