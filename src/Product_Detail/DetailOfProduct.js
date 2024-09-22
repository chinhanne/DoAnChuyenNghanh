import React, { Component } from 'react';

export default class DetailOfProduct extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedImage: '/assets/product/main_image.png', // Ảnh sản phẩm chính
      selectedOffer: '', // Lưu trữ ưu đãi được chọn
      product: {
        title: 'NVIDIA GeForce RTX 3080',
        description: 'GPU cao cấp dành cho các game thủ và người dùng đòi hỏi hiệu năng cao.',
        price: '$799',
        rating: 4.5,
        reviews: 1200,
        purchases: 3200,
        images: [
          '/assets/product/rtx_3080.png',
          '/assets/product/rtx_3080.png',
          '/assets/product/rtx_3080.png',
        ],
        offers: [
          'Giảm 10% cho thành viên VIP',
          'Miễn phí giao hàng toàn quốc',
          'Bảo hành 3 năm chính hãng',
        ],
        details: [
          { label: 'Thương hiệu', value: 'NVIDIA' },
          { label: 'Chipset', value: 'GeForce RTX 3080' },
          { label: 'Dung lượng bộ nhớ', value: '10GB GDDR6X' },
          { label: 'Tốc độ bộ nhớ', value: '19 Gbps' },
          { label: 'Cổng kết nối', value: 'HDMI, DisplayPort' },
        ],
      },
    };
  }

  // Xử lý khi người dùng nhấp vào ảnh phụ
  handleImageClick = (image) => {
    this.setState({ selectedImage: image });
  };

  // Xử lý chọn ưu đãi
  handleOfferSelect = (offer) => {
    this.setState({ selectedOffer: offer });
  };

  // Xử lý thêm vào giỏ hàng
  handleAddToCart = () => {
    const { product, selectedOffer } = this.state;
    alert(`Đã thêm ${product.title} vào giỏ hàng với ưu đãi: ${selectedOffer || 'Không có ưu đãi'}`);
    // Ở đây có thể gọi một hàm để cập nhật trạng thái giỏ hàng trong ứng dụng
  };

  // Xử lý thêm vào yêu thích
  handleAddToFavorites = () => {
    const { product } = this.state;
    alert(`Đã thêm ${product.title} vào danh sách yêu thích.`);
    // Ở đây có thể gọi một hàm để cập nhật danh sách yêu thích trong ứng dụng
  };

  render() {
    const { selectedImage, selectedOffer, product } = this.state;

    return (
      <div className="container-fluid p-3" style={{ backgroundColor: '#f8f9fa' }}>
        <div className="row">
          {/* Phần ảnh sản phẩm */}
          <div className="col-md-8">
            <div className="row">
              {/* Ảnh chính */}
              <div className="col-8">
                <img
                  src={selectedImage}
                  alt="Main product"
                  style={{ width: '100%', height: 'auto', borderRadius: '8px', objectFit: 'cover' }}
                />
              </div>
              {/* Ảnh phụ */}
              <div className="col-4 d-flex flex-column justify-content-between">
                {product.images.map((image, index) => (
                  <img
                    key={index}
                    src={image}
                    alt={`Thumbnail ${index + 1}`}
                    onClick={() => this.handleImageClick(image)}
                    style={{
                      cursor: 'pointer',
                      borderRadius: '8px',
                      border: selectedImage === image ? '2px solid #dc3545' : '1px solid #ddd',
                      marginBottom: '10px',
                    }}
                  />
                ))}
              </div>
            </div>
          </div>

          {/* Phần thông tin sản phẩm */}
          <div className="col-md-4">
            <h2 style={{ color: '#212529' }}>{product.title}</h2>
            <p style={{ color: '#495057' }}>{product.description}</p>

            {/* Giá và đánh giá */}
            <div className="d-flex justify-content-between align-items-center mb-3">
              <h4 style={{ color: '#dc3545', fontWeight: 'bold' }}>{product.price}</h4>
              <div>
                <span style={{ fontSize: '1.2em', color: '#ffc107' }}>
                  {'★'.repeat(Math.floor(product.rating))}{' '}
                  {'☆'.repeat(5 - Math.floor(product.rating))}
                </span>
                <span style={{ color: '#495057' }}> ({product.reviews} reviews)</span>
              </div>
            </div>

            {/* Lượt mua */}
            <div className="mb-3">
              <span>Lượt mua: </span>
              <strong>{product.purchases}</strong>
            </div>

            {/* Chọn ưu đãi */}
            <div className="mb-3">
              <h5 style={{ color: '#212529' }}>Chọn ưu đãi:</h5>
              {product.offers.map((offer, index) => (
                <div key={index}>
                  <input
                    type="radio"
                    id={`offer-${index}`}
                    name="offer"
                    value={offer}
                    onChange={() => this.handleOfferSelect(offer)}
                    checked={selectedOffer === offer}
                  />
                  <label htmlFor={`offer-${index}`} style={{ marginLeft: '8px', color: '#495057' }}>
                    {offer}
                  </label>
                </div>
              ))}
            </div>

            {/* Nút mua hàng và thêm vào yêu thích */}
            <div className="mb-3">
              <button
                className="btn btn-primary btn-lg w-100"
                onClick={this.handleAddToCart}
                style={{ backgroundColor: '#dc3545', borderColor: '#dc3545' }}
              >
                Mua Ngay
              </button>
              <button
                className="btn btn-outline-danger btn-lg w-100 mt-2"
                onClick={this.handleAddToFavorites}
              >
                Thêm vào yêu thích
              </button>
            </div>
          </div>
        </div>

        {/* Phần chi tiết sản phẩm */}
        <div className="row mt-4">
          <div className="col-12">
            <h4 style={{ color: '#212529' }}>Chi tiết sản phẩm</h4>
            <table className="table table-bordered">
              <tbody>
                {product.details.map((detail, index) => (
                  <tr key={index}>
                    <th>{detail.label}</th>
                    <td>{detail.value}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    );
  }
}
