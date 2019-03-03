import React, { PropTypes } from 'react';

/**
 * React <b>Alert</b> Component for Patternfly Web Components
 *
 * @requires <b>pf-alert</b> web component:
 * https://github.com/patternfly-webcomponents/patternfly-webcomponents/tree/master/src/pf-alert
 *
 * @prop {string} type danger, warning, success, info
 * @prop {bool} persistent whether to add dismiss
 *
 *
 * @example {@lang xml}
 * <Alert type="danger" persistent={true}>
 *  <span>Danger Will Robinson!</span>
 * </Alert>
 *
 */
class Alert extends React.Component {

  static propTypes = {
    type: React.PropTypes.string,
    persistent: React.PropTypes.bool
  };

  render() {
    return (
      <pf-alert 
            type={this.props.type}
            persistent={this.props.persistent}>
        {this.props.children}
      </pf-alert>
    )
  }
}

export default Alert;