import React, { Component } from 'react';

export default class Header_Main extends Component {
  render() {
    return (
      <div>
        <nav className="navbar navbar-expand-sm navbar-dark" style={navbarStyle}>
          <a className="navbar-brand" href="#" style={brandStyle}>MyBrand</a>
          <button 
            className="navbar-toggler d-lg-none" 
            type="button" 
            data-toggle="collapse" 
            data-target="#collapsibleNavId" 
            aria-controls="collapsibleNavId" 
            aria-expanded="false" 
            aria-label="Toggle navigation" 
          />
          <div className="collapse navbar-collapse" id="collapsibleNavId">
            <ul className="navbar-nav mr-auto mt-2 mt-lg-0">
              <li className="nav-item active">
                <a className="nav-link" href="#" style={navLinkStyle}>Home <span className="sr-only">(current)</span></a>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="#" style={navLinkStyle}>Link</a>
              </li>
              <li className="nav-item dropdown">
                <a className="nav-link dropdown-toggle" href="#" id="dropdownId" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style={navLinkStyle}>
                  Categories
                </a>
                <div className="dropdown-menu" aria-labelledby="dropdownId">
                  <a className="dropdown-item" href="#" style={dropdownItemStyle}>Category 1</a>
                  <a className="dropdown-item" href="#" style={dropdownItemStyle}>Category 2</a>
                </div>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="#" style={navLinkStyle}>Account</a>
              </li>
            </ul>
            <form className="form-inline my-2 my-lg-0">
              <input className="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search" />
              <button className="btn btn-outline-light my-2 my-sm-0" type="submit" style={buttonStyle}>Search</button>
            </form>
            <button className="btn btn-outline-light ml-3" style={buttonStyle}>Login</button>
          </div>
        </nav>
      </div>
    );
  }
}

// CSS-in-JS styles for consistent and clean UI
const navbarStyle = {
  backgroundColor: 'black',
};

const brandStyle = {
  color: 'white',
  fontWeight: 'bold',
};

const navLinkStyle = {
  color: 'white',
  transition: 'color 0.3s ease',
};

const dropdownItemStyle = {
  color: 'black',
  transition: 'background-color 0.3s ease',
};

const buttonStyle = {
  color: 'red',
  borderColor: 'red',
  transition: 'background-color 0.3s ease, color 0.3s ease',
};

// Add hover effects
const hoverStyles = {
  '&:hover': {
    color: 'red',
  },
};
