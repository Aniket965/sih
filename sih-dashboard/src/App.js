import React, { Component } from "react";
import { Pie, PieChart, AreaChart, Area } from "recharts";
import firebase from 'firebase';
import logo from "./logo.svg";
import "./App.css";
const DATA = [
  { name: "Group A", value: 164, fill: "rgb(100,212,249)" },
  { name: "Group B", value: 36, fill: "rgb(250,107,91)" }
];
/**
 *
 * @param {String} str
 */
function  processdata(str) {
  return str.substr(2,str.length-5)
}

function map (num, in_min, in_max, out_min, out_max) {
  return (num - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
}

function classify_ac_mode(temp,humidity,isLoadHigh=false) {
  if (isLoadHigh) {
    return "Power saver"
  }else if (humidity > 50) {
    return "Dry"
  }else if (temp < 18) {
    return "fan"
  }else {
    return "cool"
  }
}

const dd = [
  { name: "Page A", uv: 5, pv: 3800, amt: 2400 },
  { name: "Page B", uv: 8, pv: 1398, amt: 2210 },
  { name: "Page C", uv: 7, pv: 9800, amt: 2290 },
  { name: "Page D", uv: 10, pv: 3908, amt: 2000 },
  { name: "Page E", uv: 12, pv: 4800, amt: 2181 },
  { name: "Page F", uv: 10, pv: 2000, amt: 2500 },
  { name: "Page g", uv: 6, pv: 2000, amt: 2500 }
];

const idd = [
  { name: "Page A", uv: 4, pv: 3800, amt: 2400 },
  { name: "Page B", uv: 6, pv: 1398, amt: 2210 },
  { name: "Page C", uv: 5, pv: 9800, amt: 2290 },
  { name: "Page D", uv: 7, pv: 3908, amt: 2000 },
  { name: "Page E", uv: 10, pv: 4800, amt: 2181 },
  { name: "Page F", uv: 8, pv: 2000, amt: 2500 },
  { name: "Page g", uv: 4, pv: 2000, amt: 2500 }
];

const wodd = [
  { name: "Page A", uv: 15, pv: 3800, amt: 2400 },
  { name: "Page B", uv: 17, pv: 1398, amt: 2210 },
  { name: "Page C", uv: 16, pv: 9800, amt: 2290 },
  { name: "Page D", uv: 15, pv: 3908, amt: 2000 },
  { name: "Page E", uv: 16, pv: 4800, amt: 2181 },
  { name: "Page F", uv: 18, pv: 2000, amt: 2500 },
  { name: "Page g", uv: 16, pv: 2000, amt: 2500 }
]

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data:[],
      prediction:[0.8340184875510961, 0.8512286324786323, 0.8684387774061686, 0.8856489223337048, 0.9028590672612411, 0.9200692121887772, 0.9372793571163135, 0.9544895020438497, 0.971699646971386, 0.9889097918989223, 1.0061199368264584, 1.0233300817539948, 1.040540226681531, 1.057750371609067, 1.0749605165366034, 1.0921706614641395, 1.109380806391676, 1.126590951319212, 1.1438010962467482, 1.1610112411742846, 1.1782213861018207, 1.195431531029357, 1.2126416759568932, 1.2298518208844293]
    }
  }

 componentWillMount() {
  var config = {
    apiKey: "AIzaSyCOmubrc3gEd6LOW5UfRH5LVaL-GFgRCgk",
    authDomain: "not-so-awesome-project-45a2e.firebaseapp.com",
    databaseURL: "https://not-so-awesome-project-45a2e.firebaseio.com",
    projectId: "not-so-awesome-project-45a2e",
    storageBucket: "not-so-awesome-project-45a2e.appspot.com",
    messagingSenderId: "481329884022"
  };
  firebase.initializeApp(config);

  var ref  = firebase.database().ref('/sensors')
  ref.limitToLast(1).on('child_added',(snapshot)=>{
    let data = processdata( snapshot.val()['data']).split(',');
    this.setState({
      data,
      time:snapshot.val()['time']
    })
  })

  fetch('http://localhost:5500/predictpower?month=3')
  .then(res => JSON.parse(res))
  .then(res => {
    console.log(res[2])
  })

 }



  render() {
    { console.log(this.state.data)}
    return (
      <div className="container">
        <div className="demographs">
          <div className="emotions">
            <div className="header">
              <h1>Appliance Power Usage</h1>

            </div>
            <div className="content-seven">
              <div className="item1">
                <div className="emoji">💡</div>
                <div className="fill-happy" style={{height:`${map(this.state.data[5],-0.1,1,0,150)}px`}} />
              </div>
              <div className="item2">
                <div className="emoji">❄️</div>
                <div className="fill-happy" style={{height:`${map(this.state.data[6],-0.1,1,0,150)}px`}} />
              </div>
              <div className="item3">
                <div className="emoji">💨</div>
                <div className="fill-happy" style={{height:`0px`}} />
              </div>

            </div>
          </div>

          <div className="gender">
            <div className="header">
              <h1>RoomWise Power(Monthly units)</h1>
            </div>
            <div className="content-gender">
              <div className="male">
                <h3>Room 1</h3>
                <h1> {DATA[0].value}</h1>
              </div>

              <div>
                <PieChart width={283} height={210}>
                  <Pie
                    data={DATA}
                    cx="50%"
                    cy="50%"
                    outerRadius={70}
                    fill="rgb(250,107,91)"
                    label
                  />
                </PieChart>

              </div>
              <div className="female">
                <h3>Room 2</h3>
                <h1> {DATA[1].value}</h1>
              </div>
            </div>
          </div>
        </div>
        <div className="views">
          <div className="header">
            <h1>Power Usage (Units)</h1>
          </div>
          <div className="content-seven days">
            <div className="lol">

              <AreaChart
                style={{ position: "absolute" }}
                width={1435}
                height={220}
                data={dd}
              >
                <Area
                  type="monotone"
                  dataKey="uv"
                  stroke="red"
                  fill="orangered"
                />
              </AreaChart>

            <AreaChart
                style={{ position: "absolute" }}
                width={1435}
                height={220}
                data={idd}
              >
                <Area
                  type="monotone"
                  dataKey="uv"
                  stroke="green"
                  fill="rgb(66, 244, 101)"
                />
              </AreaChart>
                    <AreaChart
                style={{ position: "absolute" }}
                width={1435}
                height={220}
                data={wodd}
              >
                <Area
                  type="monotone"
                  dataKey="uv"
                  stroke="blue"
                  fill="skyblue"
                />
              </AreaChart>
            </div>
            <div className="monday day">
              <h1>Monday</h1>

              <h3> {dd[0].uv}</h3>
            </div>
            <div className="tuesday day">
              <h1>Tuesday</h1>
              <h3> {dd[1].uv}</h3>
            </div>
            <div className="wednesday day">
              <h1>Wednesday</h1>
              <h3> {dd[2].uv}</h3>
            </div>
            <div className="thursday day">
              <h1>Thursday</h1>
              <h3> {dd[3].uv}</h3>
            </div>
            <div className="friday day">
              <h1>Friday</h1>
              <h3> {dd[4].uv}</h3>
            </div>
            <div className="saturday day">
              <h1>Saturday</h1>
              <h3> {dd[5].uv}</h3>
            </div>
            <div className="sunday day">
              <h1>Sunday</h1>
              <h3> {dd[6].uv}</h3>
            </div>
          </div>
        </div>

        <div className="cards">
          <div className="header">
            <h1>Demographs( This Month)</h1>
          </div>
          <div className="content-seven">
            <div className="card hot-section">
              <h5>Most Heavy</h5>
              <h1>AC</h1>
            </div>
            <div className="card currentstocks">
              <h5>Predicted Power(this hour KWH)</h5>
              <h1>{this.state.prediction[2].toFixed(4)}</h1>
            </div>
            <div className="card currentViewrs">
            <h5>Predicted Bill Price</h5>
              <h1> 1770 ₹</h1>
            </div>
            <div className="card currentSexRation">
              <h5>Temperature </h5>
              <h1>{this.state.data[2]}°C</h1>
            </div>
            <div className="card avgage">
              <h5>Suggested Ac Mode</h5>
              <h1>{classify_ac_mode(parseInt(this.state.data[2]),parseInt(this.state.data[2]))}</h1>
            </div>
            <div className="card activetime">
              <h5>Humidity %</h5>
              <h1> {this.state.data[3]} </h1>
            </div>
            <div className="card hotProduct">
              <h5>Room 1 Light</h5>
              <h1> { parseFloat(this.state.data[4]) > 700 ? "Enough Light": "💡"}</h1>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default App;
