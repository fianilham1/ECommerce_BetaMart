import React, { Component } from 'react';
import './header.css';


class Header extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    render() {
        return (
            <>
                <div className="bg">
                    <div className="head1">List of</div>
                    <h2 className="head2">User</h2>
                    <div className="bgImg"></div>
                </div>
                
            </>
        );
    }
}

export default Header;