import React, { Component } from 'react';
import "./menu.css"

class Menu extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isMouse:false
        }
    }

    // onMouseEvent = () => {
    //     this.setState({
    //         isMouse:!this.state.isMouse
    //     })
    // }

    render() {
        const { isActivePage, redirect, children} = this.props
        return (
            <div className={`menu ${isActivePage} ${this.state.isMouse ? 'color' : ''}`} onClick={redirect}>{children}</div>
        );
    }
}

export default Menu;