import React, { Component } from 'react';

// Component cho từng danh mục sản phẩm
class ProductCategory extends Component {
  render() {
    const { title, products, onButtonClick, onPrevClick, onNextClick, currentIndex } = this.props;

    // Tính toán các sản phẩm sẽ hiển thị dựa trên currentIndex
    const itemsPerPage = 4; // Số lượng sản phẩm hiển thị trong mỗi trang
    const startIndex = currentIndex * itemsPerPage;
    const currentProducts = products.slice(startIndex, startIndex + itemsPerPage);

    return (
      <div className="body_main_card" style={bodyMainCardStyle}>
        <h1 style={titleStyle}>{title}</h1> {/* Tiêu đề của danh mục sản phẩm */}
        <div className="row">
          {currentProducts.map((product, index) => (
            <div className="col-md-3" key={index}>
              <div className="card border-dark" style={cardStyle}>
                <img
                  className="card-img-top"
                  src={product.image}
                  alt={`${product.title} image`} // Mô tả hình ảnh
                  style={{ height: '180px', objectFit: 'cover' }} 
                />
                <div className="card-body">
                  <h4 className="card-title" style={{ fontWeight: 'bold' }}>{product.title}</h4> {/* Tiêu đề sản phẩm */}
                  <p className="card-text">{product.description}</p> {/* Mô tả sản phẩm */}
                  <button
                    className="btn btn-primary"
                    style={buttonStyle}
                    onClick={() => onButtonClick(product.title)} // Gọi hàm khi nhấn nút
                  >
                    Learn More
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
        <div className="d-flex justify-content-between mt-3">
          <button className="btn btn-secondary" onClick={onPrevClick} disabled={startIndex === 0}>Prev</button>
          <button className="btn btn-secondary" onClick={onNextClick} disabled={startIndex + itemsPerPage >= products.length}>Next</button>
        </div>
      </div>
    );
  }
}

// Component chính
export default class Body_Main extends Component {
  constructor(props) {
    super(props);
    // Khởi tạo state cho từng danh mục sản phẩm
    this.state = {
      currentCategoryIndex: 0,
      productIndices: [0, 0, 0, 0], // Chỉ số cho từng danh mục (BEST SALE, KEYBOARDS, MONITORS, GEAR)
    };
  }

  handleButtonClick = (title) => {
    alert(`You clicked on: ${title}`); // Hiển thị thông báo khi nhấn nút
  };

  handlePrevClick = (categoryIndex) => {
    this.setState((prevState) => {
      const newProductIndices = [...prevState.productIndices];
      newProductIndices[categoryIndex] = Math.max(newProductIndices[categoryIndex] - 1, 0);
      return { productIndices: newProductIndices };
    });
  };

  handleNextClick = (categoryIndex) => {
    this.setState((prevState) => {
      const newProductIndices = [...prevState.productIndices];
      newProductIndices[categoryIndex] = Math.min(newProductIndices[categoryIndex] + 1, 2); // Giới hạn không cho lớn hơn số lượng danh mục - 1
      return { productIndices: newProductIndices };
    });
  };

  render() {
    // Dữ liệu các danh mục sản phẩm
    const categories = [
      {
        title: 'BEST SALE',
        products: this.getProductData(), // Lấy dữ liệu sản phẩm
      },
      {
        title: 'KEYBOARDS',
        products: this.getKeyboardData(), // Lấy dữ liệu bàn phím
      },
      {
        title: 'MONITORS',
        products: this.getMonitorData(), // Lấy dữ liệu màn hình
      },
      {
        title: 'GEAR',
        products: this.getGearData(), // Lấy dữ liệu gear
      },
    ];

    return (
      <div className="body_main" style={bodyMainStyle}>
        {/* Phần trên cùng với hàng thông tin */}
        <div className="body_main_top" style={marginBottomStyle}>
          <div className="row">
            {this.renderInfoBoxes()} {/* Hiển thị các ô thông tin */}
          </div>
        </div>

        {/* Vẽ từng danh mục sản phẩm */}
        {categories.map((category, index) => (
          <ProductCategory
            key={index} // Khóa duy nhất cho mỗi danh mục
            title={category.title} // Tiêu đề danh mục
            products={category.products} // Dữ liệu sản phẩm trong danh mục
            onButtonClick={this.handleButtonClick} // Hàm xử lý sự kiện nhấn nút
            onPrevClick={() => this.handlePrevClick(index)} // Hàm xử lý sự kiện nút Prev cho danh mục cụ thể
            onNextClick={() => this.handleNextClick(index)} // Hàm xử lý sự kiện nút Next cho danh mục cụ thể
            currentIndex={this.state.productIndices[index]} // Chỉ số sản phẩm hiện tại của danh mục
          />
        ))}
      </div>
    );
  }

  // Hàm vẽ các ô thông tin
  renderInfoBoxes() {
    const features = [
      { title: 'Feature 1', description: 'Quick description of Feature 1' },
      { title: 'Feature 2', description: 'Quick description of Feature 2' },
      { title: 'Feature 3', description: 'Quick description of Feature 3' },
      { title: 'Feature 4', description: 'Quick description of Feature 4' },
    ];

    return features.map((feature, index) => (
      <div className="col-md-3" key={index}>
        <div className="info-box" style={infoBoxStyle}>
          <h5>{feature.title}</h5> {/* Tiêu đề của ô thông tin */}
          <p>{feature.description}</p> {/* Mô tả của ô thông tin */}
        </div>
      </div>
    ));
  }

  // Hàm lấy dữ liệu sản phẩm cho danh mục "BEST SALE"
  getProductData() {
    return [
      { title: 'Title 1', description: 'This is a brief description of the card content.', image: 'your-image-url-1.jpg' },
      { title: 'Title 2', description: 'This is a brief description of the card content.', image: 'your-image-url-2.jpg' },
      { title: 'Title 3', description: 'This is a brief description of the card content.', image: 'your-image-url-3.jpg' },
      { title: 'Title 4', description: 'This is a brief description of the card content.', image: 'your-image-url-4.jpg' },
      { title: 'Title 5', description: 'This is a brief description of the card content.', image: 'your-image-url-5.jpg' },
      { title: 'Title 6', description: 'This is a brief description of the card content.', image: 'your-image-url-6.jpg' },
    ];
  }

  // Hàm lấy dữ liệu sản phẩm cho danh mục "KEYBOARDS"
  getKeyboardData() {
    return [
      { title: 'Keyboard 1', description: 'Mechanical Keyboard.', image: 'your-keyboard-image-url-1.jpg' },
      { title: 'Keyboard 2', description: 'Wireless Keyboard.', image: 'your-keyboard-image-url-2.jpg' },
      { title: 'Keyboard 3', description: 'Gaming Keyboard.', image: 'your-keyboard-image-url-3.jpg' },
      { title: 'Keyboard 4', description: 'Compact Keyboard.', image: 'your-keyboard-image-url-4.jpg' },
      { title: 'Keyboard 5', description: 'Ergonomic Keyboard.', image: 'your-keyboard-image-url-5.jpg' },
      { title: 'Keyboard 6', description: 'RGB Keyboard.', image: 'your-keyboard-image-url-6.jpg' },
    ];
  }

  // Hàm lấy dữ liệu sản phẩm cho danh mục "MONITORS"
  getMonitorData() {
    return [
      { title: 'Monitor 1', description: '27 inch Monitor.', image: 'your-monitor-image-url-1.jpg' },
      { title: 'Monitor 2', description: '4K Monitor.', image: 'your-monitor-image-url-2.jpg' },
      { title: 'Monitor 3', description: 'Curved Monitor.', image: 'your-monitor-image-url-3.jpg' },
      { title: 'Monitor 4', description: 'Gaming Monitor.', image: 'your-monitor-image-url-4.jpg' },
      { title: 'Monitor 5', description: 'Ultrawide Monitor.', image: 'your-monitor-image-url-5.jpg' },
      { title: 'Monitor 6', description: 'Portable Monitor.', image: 'your-monitor-image-url-6.jpg' },
    ];
  }

  // Hàm lấy dữ liệu sản phẩm cho danh mục "GEAR"
  getGearData() {
    return [
      { title: 'Gear 1', description: 'Gaming Mouse.', image: 'your-gear-image-url-1.jpg' },
      { title: 'Gear 2', description: 'Headset.', image: 'your-gear-image-url-2.jpg' },
      { title: 'Gear 3', description: 'Webcam.', image: 'your-gear-image-url-3.jpg' },
      { title: 'Gear 4', description: 'Gaming Chair.', image: 'your-gear-image-url-4.jpg' },
      { title: 'Gear 5', description: 'Microphone.', image: 'your-gear-image-url-5.jpg' },
      { title: 'Gear 6', description: 'Capture Card.', image: 'your-gear-image-url-6.jpg' },
    ];
  }
}

// CSS-in-JS styles for consistent and clean UI
const bodyMainStyle = {
  padding: '20px',
  backgroundColor: '#f8f9fa', // Màu nền cho toàn bộ body
};

const marginBottomStyle = {
  marginBottom: '30px', // Khoảng cách dưới cho phần trên
};

const infoBoxStyle = {
  backgroundColor: '#ffffff', // Màu nền của ô thông tin
  padding: '15px',
  borderRadius: '8px',
  boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.1)', // Đổ bóng cho ô
  textAlign: 'center', // Căn giữa nội dung
  marginBottom: '20px', // Khoảng cách dưới cho ô
  transition: 'transform 0.2s, box-shadow 0.2s', // Hiệu ứng chuyển tiếp khi hover
};

const bodyMainCardStyle = {
  border: '2px solid #000', // Đường viền cho khung danh mục sản phẩm
  borderRadius: '8px',
  padding: '20px',
  backgroundColor: '#fff', // Màu nền khung danh mục
};

const titleStyle = {
  textAlign: 'left', // Căn trái cho tiêu đề
  marginBottom: '20px',
  color: '#dc3545', // Màu đỏ cho tiêu đề
};

const cardStyle = {
  borderRadius: '8px',
  overflow: 'hidden',
  height: '300px', // Đặt chiều cao cố định cho các ô sản phẩm
  marginBottom: '20px', // Khoảng cách dưới cho thẻ sản phẩm
};

const buttonStyle = {
  padding: '10px 20px',
  borderRadius: '5px',
  fontWeight: '500',
  backgroundColor: '#dc3545', // Màu nền nút đỏ
  color: '#fff', // Màu chữ trắng
  border: 'none',
  cursor: 'pointer', // Hiển thị con trỏ như bàn tay khi hover
  marginTop: '10px', // Khoảng cách trên cho nút
};
