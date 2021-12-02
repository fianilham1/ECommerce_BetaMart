import React, { Component } from 'react';
import "./input.css"

class Menu extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }

    renderInput = () => {
        const {name, typeInput, focus, blur, typeTx, defaultValue, handleChange} = this.props
       if(typeInput) return ( 
        <div>
        <h5>{name}</h5>
        <select name={name.toLowerCase()} className="role" onChange={handleChange}>
            <option value="Manager">Manager</option>
            <option value="HRD">HRD</option>
            <option value="Employee">Employee</option>
        </select>
         </div>
       )

    return (<div>
                <h5>{name}</h5>
                <input id={`isFocus${name}`} className="input" type={typeTx} onFocus={focus} onBlur={blur} name={name.toLowerCase()} defaultValue={defaultValue}/>
            </div>)
    }

    render() {
        const { focusState, icon, name } = this.props
        return (
            <>
            <div className={`input-div${ focusState[`isFocus${name}`] ? ' focus' : ''}`}>
                <div className="i">
                    {icon}      
                </div>
                {this.renderInput()}
                {/* <div>
                    <h5>{name}</h5>
                <input id={`isFocus${name}`} className="input" type={typeTx} onFocus={focus} onBlur={blur} name={name.toLowerCase()} defaultValue={defaultValue}/>
                </div> */}
            </div>
            </>
        );
    }
}

export default Menu;