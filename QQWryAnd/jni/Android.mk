
LOCAL_PATH := $(call my-dir)

#other prebuilt shared library
include $(CLEAR_VARS)
LOCAL_MODULE := un7z
LOCAL_SRC_FILES := libun7z.so
include $(PREBUILT_SHARED_LIBRARY)

#qqwry shared library
include $(CLEAR_VARS)

LOCAL_LDLIBS+= -llog

LOCAL_MODULE    := qqand

LOCAL_CFLAGS := -DDEBUG

LOCAL_SRC_FILES := qqand.cpp \
				   IPLocator.cpp

include $(BUILD_SHARED_LIBRARY)
