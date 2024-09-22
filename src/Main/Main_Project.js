import React, { Component } from 'react';
import Header_Main from './Header_Main';
import Left_Main from './Left_Main';
import Body_Main from './Body_Main';
import Right_Main from './Right_Main';
import Bottom_Main from './Bottom_Main';
import Top_Main from './Top_Main';

export default class Main_Project extends Component {
  render() {
    return (
      <div className="container-fluid">
        <Header_Main />
        <Top_Main/>
        <div className="row">
          <div  className="col-2"><Left_Main /></div>
          <div  className="col-8"><Body_Main /></div>
          <div  className="col-2"><Right_Main /></div>
        </div>

        <div className="row">
          <div className="col-12"><Bottom_Main /></div>
        </div>
      </div>
    );
  }
}