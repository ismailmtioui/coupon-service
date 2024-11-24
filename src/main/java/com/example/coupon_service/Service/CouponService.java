package com.example.coupon_service.Service;

import com.example.coupon_service.Repository.CouponRepository;
import com.example.coupon_service.entity.Coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    // Create a coupon
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    // Get all coupons
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    // Get a coupon by ID
    public Optional<Coupon> getCouponById(Long id) {
        return couponRepository.findById(id);
    }

    // Update a coupon
    public Coupon updateCoupon(Long id, Coupon couponDetails) {
        Optional<Coupon> existingCoupon = couponRepository.findById(id);
        if (existingCoupon.isPresent()) {
            Coupon coupon = existingCoupon.get();
            coupon.setCode(couponDetails.getCode());
            coupon.setExpirationDate(couponDetails.getExpirationDate());
            coupon.setDiscountPercentage(couponDetails.getDiscountPercentage());
            return couponRepository.save(coupon);
        }
        return null; // Return null or throw an exception if the coupon isn't found
    }

    // Delete a coupon
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }
    public String applyCoupon(String code, double originalAmount) {
        Optional<Coupon> couponOpt = couponRepository.findAll().stream()
                .filter(coupon -> coupon.getCode().equals(code))
                .findFirst();

        if (couponOpt.isPresent()) {
            Coupon coupon = couponOpt.get();

            // Check if the coupon is expired
            if (coupon.getExpirationDate().before(new Date())) {
                return "Coupon has expired.";
            }

            // Calculate the discount
            double discount = originalAmount * coupon.getDiscountPercentage() / 100;
            double finalAmount = originalAmount - discount;

            return "Coupon applied! Discount: " + discount + ", Final amount: " + finalAmount;
        } else {
            return "Invalid coupon code.";
        }
    }
}
