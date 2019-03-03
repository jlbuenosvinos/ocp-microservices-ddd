'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

/**
 * React <b>UtilizationBarChart</b> Component for Patternfly Web Components
 *
 * @requires <b>pf-utilization-bar-chart</b> web component:
 * https://github.com/patternfly-webcomponents/patternfly-webcomponents/tree/master/src/pf-utilization-bar-chart
 *
 * @prop {string} chartTitle the chart title
 * @prop {string} layout 'inline' for inline layout
 * @prop {number} used the percentage used
 * @prop {number} total the percentage total
 * @prop {string} units the display units
 * @prop {number} thresholdWarning the warning threshold
 * @prop {number} thresholdError the error threshold
 * @prop {function} onThresholdSet callback function when threshold changes
 *
 *
 * @example {@lang xml}
 * <UtilizationBarChart  layout="default"
        chartTitle="RAM Usage" 
        used={this.state.used}
        total="100"
        units="MB"
        thresholdWarning="60"
        thresholdError="85"
        onThresholdSet={ this.onThresholdSet.bind(this)}>
 *  <span>Tooltip content</span>
 * </UtilizationBarChart>
 *
 */
var UtilizationBarChart = function (_React$Component) {
  _inherits(UtilizationBarChart, _React$Component);

  function UtilizationBarChart() {
    _classCallCheck(this, UtilizationBarChart);

    return _possibleConstructorReturn(this, (UtilizationBarChart.__proto__ || Object.getPrototypeOf(UtilizationBarChart)).apply(this, arguments));
  }

  _createClass(UtilizationBarChart, [{
    key: 'componentDidUpdate',
    value: function componentDidUpdate() {
      var _this2 = this;

      this.refs.pfUtilizationBarChar.addEventListener('onThresholdSet', function (e) {
        if (_this2.props.onThresholdSet) {
          _this2.props.onThresholdSet(e);
        }
      });
    }
  }, {
    key: 'render',
    value: function render() {
      return _react2.default.createElement('pf-utilization-bar-chart', { key: 'pf-utilization-bar-chart',
        'chart-title': this.props.chartTitle,
        layout: this.props.layout,
        used: this.props.used,
        total: this.props.total,
        units: this.props.units,
        'threshold-warning': this.props.thresholdWarning,
        'threshold-error': this.props.thresholdError,
        ref: 'pfUtilizationBarChar' });
    }
  }]);

  return UtilizationBarChart;
}(_react2.default.Component);

UtilizationBarChart.propTypes = {
  chartTitle: _react2.default.PropTypes.string,
  layout: _react2.default.PropTypes.string,
  used: _react2.default.PropTypes.number,
  total: _react2.default.PropTypes.number,
  units: _react2.default.PropTypes.string,
  thresholdWarning: _react2.default.PropTypes.number,
  thresholdError: _react2.default.PropTypes.number,
  onThresholdSet: _react2.default.PropTypes.func
};
exports.default = UtilizationBarChart;