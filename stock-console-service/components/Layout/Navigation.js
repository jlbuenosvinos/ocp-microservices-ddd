import React from 'react';
import Link from '../Link';
import history from '../../core/history';
import PfBreakpoints from './PfBreakpoints'; // eslint-disable-line no-unused-vars
import PfVerticalNavigation from './PfVerticalNavigation'; // eslint-disable-line no-unused-vars

class Navigation extends React.Component {

  componentDidMount() {
    // Initialize the vertical navigation
    $().setupVerticalNavigation(true);
  }

  render() {

    const location = history.getCurrentLocation();
    const homeRoutes = ['/', '/home'];

    return (
      <div className="nav-pf-vertical">
        <ul className="list-group">

          <li className={`list-group-item${location.pathname === '/home' ? ' active' : ''}`}>
            <Link to="/home">
              <span className="fa fa-dashboard" data-toggle="tooltip" title="Stock"></span>
              <span className="list-group-item-value">Stock</span>
            </Link>
          </li>

        </ul>
      </div>
    );
  }

}

export default Navigation;
