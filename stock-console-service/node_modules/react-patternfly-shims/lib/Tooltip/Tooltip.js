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
 * React <b>Tooltip</b> Component for Patternfly Web Components
 *
 * @requires <b>pf-tooltip</b> web component:
 * https://github.com/patternfly-webcomponents/patternfly-webcomponents/tree/master/src/pf-tooltip
 *
 * @prop {string} animation the animation class
 * @prop {string} targetSelector the target element selector
 * @prop {string} placement left, right, top, bottom
 * @prop {number} delay animation delay (ms)
 * @prop {number} duration animation duration (ms)
 * @prop {string} containerSelector the container element selector
 *
 *
 * @example {@lang xml}
 * <Tooltip animation="fade" targetSelector="#input1" placement="right" delay={50} duration={150} containerSelector="#container">
 *  <span>Tooltip content</span>
 * </Tooltip>
 *
 */
var Tooltip = function (_React$Component) {
  _inherits(Tooltip, _React$Component);

  function Tooltip() {
    _classCallCheck(this, Tooltip);

    return _possibleConstructorReturn(this, (Tooltip.__proto__ || Object.getPrototypeOf(Tooltip)).apply(this, arguments));
  }

  _createClass(Tooltip, [{
    key: 'componentDidUpdate',
    value: function componentDidUpdate() {
      this.handleContentChanged();
    }
  }, {
    key: 'componentDidMount',
    value: function componentDidMount() {
      this.handleContentChanged();
    }
  }, {
    key: 'handleContentChanged',
    value: function handleContentChanged() {
      var event = new CustomEvent('handleContentChanged', {});
      this.refs.pfTooltip.dispatchEvent(event);
    }
  }, {
    key: 'render',
    value: function render() {
      return _react2.default.createElement(
        'pf-tooltip',
        { ref: 'pfTooltip',
          animation: this.props.animation,
          targetSelector: this.props.targetSelector,
          placement: this.props.placement,
          delay: this.props.delay,
          duration: this.props.duration,
          containerSelector: this.props.containerSelector },
        this.props.children
      );
    }
  }]);

  return Tooltip;
}(_react2.default.Component);

Tooltip.propTypes = {
  animation: _react2.default.PropTypes.string,
  targetSelector: _react2.default.PropTypes.string,
  placement: _react2.default.PropTypes.string,
  delay: _react2.default.PropTypes.number,
  duration: _react2.default.PropTypes.number,
  containerSelector: _react2.default.PropTypes.string
};
exports.default = Tooltip;